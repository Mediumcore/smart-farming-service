package com.sdpm.smart.farming.devicemgr.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.sdpm.smart.farming.common.exception.SmartFarmingServiceException;
import com.sdpm.smart.farming.devicemgr.entity.TraceRoute;
import com.sdpm.smart.farming.devicemgr.repository.TraceRouteRepository;
import com.sdpm.smart.farming.devicemgr.vo.TraceRouteVO;

import io.swagger.models.auth.In;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shirukai
 */
@Service
public class TraceRouteService {
    @Autowired
    private TraceRouteRepository traceRouteRepository;

    public Page<TraceRoute> queryTraceRoute(String name, Pageable pageable) {
        return traceRouteRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.hasLength(name)) {
                list.add(cb.equal(root.get("name"), name));
            }

            return cb.and(list.toArray(new Predicate[0]));
        }, pageable);
    }

    public TraceRouteVO findTraceRoute(Integer routeId) {
        Optional<TraceRoute> optional = traceRouteRepository.findById(routeId);
        if (!optional.isPresent()) {
            throw new SmartFarmingServiceException("路线不存在！");
        }
        return TraceRouteVO.toVO(optional.get());
    }

    public TraceRouteVO saveTraceRoute(TraceRouteVO traceRouteVO) {
        Integer traceId = traceRouteVO.getId();

        Optional<TraceRoute> traceRouteOptional = Optional.empty();
        if (traceId != null) {
            traceRouteOptional = traceRouteRepository.findById(traceId);
        }
        TraceRoute traceRoute = traceRouteOptional.orElse(new TraceRoute());

        traceRoute.setCenter(traceRouteVO.getCenter().toJSONString());
        traceRoute.setName(traceRouteVO.getName());
        traceRoute.setLocations(traceRouteVO.getLocations().toJSONString());
        return TraceRouteVO.toVO(traceRouteRepository.save(traceRoute));
    }

    @Transactional
    public List<TraceRouteVO> kmlUpload(String kml) {
        List<Object> placeMarks = (ArrayList) JSONPath.read(XML.toJSONObject(kml).toString(), "$..Placemark");
        List<TraceRouteVO> traceRouteVOS = new ArrayList<>();
        placeMarks.forEach(o -> {
            JSONObject placeMark = (JSONObject) o;
            if (placeMark.containsKey("LineString")) {
                TraceRoute traceRoute = new TraceRoute();
                String name = placeMark.getString("name");
                JSONObject lookAt = placeMark.getJSONObject("LookAt");
                String coordinates = placeMark.getJSONObject("LineString").getString("coordinates");
                List<double[]> lines = Arrays.stream(coordinates.split(" ")).map(s -> {
                    String[] tokens = s.split(",");
                    return new double[]{Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1])};
                }).collect(Collectors.toList());

                if (lookAt != null) {
                    double[] center = new double[]{lookAt.getDouble("longitude"), lookAt.getDouble("latitude")};
                    traceRoute.setCenter(JSON.toJSONString(center));
                } else {
                    if (!lines.isEmpty()) {
                        traceRoute.setCenter(JSON.toJSONString(lines.get(0)));
                    }
                }
                traceRoute.setName(name);
                traceRoute.setLocations(JSON.toJSONString(lines));
                traceRoute = traceRouteRepository.save(traceRoute);
                traceRouteVOS.add(TraceRouteVO.toVO(traceRoute));

            }
        });
        return traceRouteVOS;
    }

    public void removeRoute(Integer routeId) {
        traceRouteRepository.deleteById(routeId);
    }
}
