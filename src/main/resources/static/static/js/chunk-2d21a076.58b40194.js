(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d21a076"],{ba89:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{style:{height:.75*t.winHeight+"px",width:.68*t.winWidth+"px"},attrs:{id:"trace-monitor"},on:{dblclick:t.maximize}},[t.chart?t._e():a("div",{staticStyle:{"text-align":"center",height:"100%"}},[t._v("暂无寻迹路线")])])},i=[],r=(a("d81d"),a("fb6a"),a("d3b7"),a("ac1f"),a("25f0"),a("1276"),a("99af"),a("79a8"),a("9ff9"),a("ff9c"),a("0261"),a("7898"),a("a9e3"),a("b680"),a("466d"),a("4d90"),a("498a"),a("262e")),o=a("2caf"),s=a("d4ec"),h=a("bee2"),l=(a("4160"),a("dca8"),a("b64b"),a("159b"),a("53ca")),c=a("45eb"),u=a("7e84"),d=(a("caad"),a("35b3"),a("3835")),f=(a("a434"),a("5319")," "),p=function(){function t(){Object(s["a"])(this,t)}return Object(h["a"])(t,null,[{key:"parse",value:function(t){if(!isNaN(parseFloat(t))&&isFinite(t))return Number(t);var e=String(t).trim().replace(/^-/,"").replace(/[NSEW]$/i,"").split(/[^0-9.,]+/);if(""==e[e.length-1]&&e.splice(e.length-1),""==e)return NaN;var a=null;switch(e.length){case 3:a=e[0]/1+e[1]/60+e[2]/3600;break;case 2:a=e[0]/1+e[1]/60;break;case 1:a=e[0];break;default:return NaN}return/^-|[WS]$/i.test(t.trim())&&(a=-a),Number(a)}},{key:"toDms",value:function(e){var a=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"d",n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:void 0;if(isNaN(e))return null;if("string"==typeof e&&""==e.trim())return null;if("boolean"==typeof e)return null;if(e==1/0)return null;if(null==e)return null;if(void 0===n)switch(a){case"d":case"deg":n=4;break;case"dm":case"deg+min":n=2;break;case"dms":case"deg+min+sec":n=0;break;default:a="d",n=4;break}e=Math.abs(e);var i=null,r=null,o=null,s=null;switch(a){default:case"d":case"deg":r=e.toFixed(n),r<100&&(r="0"+r),r<10&&(r="0"+r),i=r+"°";break;case"dm":case"deg+min":r=Math.floor(e),o=(60*e%60).toFixed(n),60==o&&(o=(0).toFixed(n),r++),r=("000"+r).slice(-3),o<10&&(o="0"+o),i=r+"°"+t.separator+o+"′";break;case"dms":case"deg+min+sec":r=Math.floor(e),o=Math.floor(3600*e/60)%60,s=(3600*e%60).toFixed(n),60==s&&(s=(0).toFixed(n),o++),60==o&&(o=0,r++),r=("000"+r).slice(-3),o=("00"+o).slice(-2),s<10&&(s="0"+s),i=r+"°"+t.separator+o+"′"+t.separator+s+"″";break}return i}},{key:"toLat",value:function(e,a,n){var i=t.toDms(t.wrap90(e),a,n);return null===i?"–":i.slice(1)+t.separator+(e<0?"S":"N")}},{key:"toLon",value:function(e,a,n){var i=t.toDms(t.wrap180(e),a,n);return null===i?"–":i+t.separator+(e<0?"W":"E")}},{key:"toBrng",value:function(e,a,n){var i=t.toDms(t.wrap360(e),a,n);return null===i?"–":i.replace("360","0")}},{key:"fromLocale",value:function(t){var e=123456.789.toLocaleString(),a={thousands:e.slice(3,4),decimal:e.slice(7,8)};return t.replace(a.thousands,"⁜").replace(a.decimal,".").replace("⁜",",")}},{key:"toLocale",value:function(t){var e=123456.789.toLocaleString(),a={thousands:e.slice(3,4),decimal:e.slice(7,8)};return t.replace(/,([0-9])/,"⁜$1").replace(".",a.decimal).replace("⁜",a.thousands)}},{key:"compassPoint",value:function(e){var a=arguments.length>1&&void 0!==arguments[1]?arguments[1]:3;if(![1,2,3].includes(Number(a)))throw new RangeError("invalid precision ‘".concat(a,"’"));e=t.wrap360(e);var n=["N","NNE","NE","ENE","E","ESE","SE","SSE","S","SSW","SW","WSW","W","WNW","NW","NNW"],i=4*Math.pow(2,a-1),r=n[Math.round(e*i/360)%i*16/i];return r}},{key:"wrap360",value:function(t){return 0<=t&&t<360?t:(t%360+360)%360}},{key:"wrap180",value:function(t){return-180<t&&t<=180?t:(t+540)%360-180}},{key:"wrap90",value:function(t){return-90<=t&&t<=90?t:Math.abs((t%360+270)%360-180)-90}},{key:"separator",get:function(){return f},set:function(t){f=t}}]),t}();Number.prototype.toRadians=function(){return this*Math.PI/180},Number.prototype.toDegrees=function(){return 180*this/Math.PI};var v=p,m=function(){function t(e,a,n){if(Object(s["a"])(this,t),isNaN(e)||isNaN(e)||isNaN(e))throw new TypeError("invalid vector [".concat(e,",").concat(a,",").concat(n,"]"));this.x=Number(e),this.y=Number(a),this.z=Number(n)}return Object(h["a"])(t,[{key:"plus",value:function(e){if(!(e instanceof t))throw new TypeError("v is not Vector3d object");return new t(this.x+e.x,this.y+e.y,this.z+e.z)}},{key:"minus",value:function(e){if(!(e instanceof t))throw new TypeError("v is not Vector3d object");return new t(this.x-e.x,this.y-e.y,this.z-e.z)}},{key:"times",value:function(e){if(isNaN(e))throw new TypeError("invalid scalar value ‘".concat(e,"’"));return new t(this.x*e,this.y*e,this.z*e)}},{key:"dividedBy",value:function(e){if(isNaN(e))throw new TypeError("invalid scalar value ‘".concat(e,"’"));return new t(this.x/e,this.y/e,this.z/e)}},{key:"dot",value:function(e){if(!(e instanceof t))throw new TypeError("v is not Vector3d object");return this.x*e.x+this.y*e.y+this.z*e.z}},{key:"cross",value:function(e){if(!(e instanceof t))throw new TypeError("v is not Vector3d object");var a=this.y*e.z-this.z*e.y,n=this.z*e.x-this.x*e.z,i=this.x*e.y-this.y*e.x;return new t(a,n,i)}},{key:"negate",value:function(){return new t(-this.x,-this.y,-this.z)}},{key:"unit",value:function(){var e=this.length;if(1==e)return this;if(0==e)return this;var a=this.x/e,n=this.y/e,i=this.z/e;return new t(a,n,i)}},{key:"angleTo",value:function(e){var a=arguments.length>1&&void 0!==arguments[1]?arguments[1]:void 0;if(!(e instanceof t))throw new TypeError("v is not Vector3d object");if(!(a instanceof t||void 0==a))throw new TypeError("n is not Vector3d object");var n=void 0==a||this.cross(e).dot(a)>=0?1:-1,i=this.cross(e).length*n,r=this.dot(e);return Math.atan2(i,r)}},{key:"rotateAround",value:function(e,a){if(!(e instanceof t))throw new TypeError("axis is not Vector3d object");var n=a.toRadians(),i=this.unit(),r=e.unit(),o=Math.sin(n),s=Math.cos(n),h=1-s,l=r.x,c=r.y,u=r.z,d=[[h*l*l+s,h*l*c-o*u,h*l*u+o*c],[h*l*c+o*u,h*c*c+s,h*c*u-o*l],[h*l*u-o*c,h*c*u+o*l,h*u*u+s]],f=[d[0][0]*i.x+d[0][1]*i.y+d[0][2]*i.z,d[1][0]*i.x+d[1][1]*i.y+d[1][2]*i.z,d[2][0]*i.x+d[2][1]*i.y+d[2][2]*i.z],p=new t(f[0],f[1],f[2]);return p}},{key:"toString",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:3;return"[".concat(this.x.toFixed(t),",").concat(this.y.toFixed(t),",").concat(this.z.toFixed(t),"]")}},{key:"length",get:function(){return Math.sqrt(this.x*this.x+this.y*this.y+this.z*this.z)}}]),t}();Number.prototype.toRadians=function(){return this*Math.PI/180},Number.prototype.toDegrees=function(){return 180*this/Math.PI};var y=m,g={WGS84:{a:6378137,b:6356752.314245,f:1/298.257223563}},w={WGS84:{ellipsoid:g.WGS84}};Object.freeze(g.WGS84),Object.freeze(w.WGS84);var b=function(){function t(e,a){var n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:0;if(Object(s["a"])(this,t),isNaN(e))throw new TypeError("invalid lat ‘".concat(e,"’"));if(isNaN(a))throw new TypeError("invalid lon ‘".concat(a,"’"));if(isNaN(n))throw new TypeError("invalid height ‘".concat(n,"’"));this._lat=v.wrap90(Number(e)),this._lon=v.wrap180(Number(a)),this._height=Number(n)}return Object(h["a"])(t,[{key:"toCartesian",value:function(){var t=this.datum?this.datum.ellipsoid:this.referenceFrame?this.referenceFrame.ellipsoid:g.WGS84,e=this.lat.toRadians(),a=this.lon.toRadians(),n=this.height,i=t.a,r=t.f,o=Math.sin(e),s=Math.cos(e),h=Math.sin(a),l=Math.cos(a),c=2*r-r*r,u=i/Math.sqrt(1-c*o*o),d=(u+n)*s*l,f=(u+n)*s*h,p=(u*(1-c)+n)*o;return new N(d,f,p)}},{key:"equals",value:function(e){if(!(e instanceof t))throw new TypeError("invalid point ‘".concat(e,"’"));return!(Math.abs(this.lat-e.lat)>Number.EPSILON)&&(!(Math.abs(this.lon-e.lon)>Number.EPSILON)&&(!(Math.abs(this.height-e.height)>Number.EPSILON)&&(this.datum==e.datum&&(this.referenceFrame==e.referenceFrame&&this.epoch==e.epoch))))}},{key:"toString",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"d",e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:void 0,a=arguments.length>2&&void 0!==arguments[2]?arguments[2]:null;if(!["d","dm","dms","n"].includes(t))throw new RangeError("invalid format ‘".concat(t,"’"));var n=(this.height>=0?" +":" ")+this.height.toFixed(a)+"m";if("n"==t){void 0==e&&(e=4);var i=this.lat.toFixed(e),r=this.lon.toFixed(e);return"".concat(i,", ").concat(r).concat(null==a?"":n)}var o=v.toLat(this.lat,t,e),s=v.toLon(this.lon,t,e);return"".concat(o,", ").concat(s).concat(null==a?"":n)}},{key:"lat",get:function(){return this._lat},set:function(t){if(this._lat=isNaN(t)?v.wrap90(v.parse(t)):v.wrap90(Number(t)),isNaN(this._lat))throw new TypeError("invalid lat ‘".concat(t,"’"))}},{key:"latitude",get:function(){return this._lat},set:function(t){if(this._lat=isNaN(t)?v.wrap90(v.parse(t)):v.wrap90(Number(t)),isNaN(this._lat))throw new TypeError("invalid latitude ‘".concat(t,"’"))}},{key:"lon",get:function(){return this._lon},set:function(t){if(this._lon=isNaN(t)?v.wrap180(v.parse(t)):v.wrap180(Number(t)),isNaN(this._lon))throw new TypeError("invalid lon ‘".concat(t,"’"))}},{key:"lng",get:function(){return this._lon},set:function(t){if(this._lon=isNaN(t)?v.wrap180(v.parse(t)):v.wrap180(Number(t)),isNaN(this._lon))throw new TypeError("invalid lng ‘".concat(t,"’"))}},{key:"longitude",get:function(){return this._lon},set:function(t){if(this._lon=isNaN(t)?v.wrap180(v.parse(t)):v.wrap180(Number(t)),isNaN(this._lon))throw new TypeError("invalid longitude ‘".concat(t,"’"))}},{key:"height",get:function(){return this._height},set:function(t){if(this._height=Number(t),isNaN(this._height))throw new TypeError("invalid height ‘".concat(t,"’"))}},{key:"datum",get:function(){return this._datum},set:function(t){this._datum=t}}],[{key:"parse",value:function(){for(var t=arguments.length,e=new Array(t),a=0;a<t;a++)e[a]=arguments[a];if(0==e.length)throw new TypeError("invalid (empty) point");var n=void 0,i=void 0,r=void 0;if("object"==Object(l["a"])(e[0])&&(1==e.length||!isNaN(parseFloat(e[1])))){var o=e[0];if("Point"==o.type&&Array.isArray(o.coordinates)){var s=Object(d["a"])(o.coordinates,3);i=s[0],n=s[1],r=s[2],r=r||0}else void 0!=o.latitude&&(n=o.latitude),void 0!=o.lat&&(n=o.lat),void 0!=o.longitude&&(i=o.longitude),void 0!=o.lng&&(i=o.lng),void 0!=o.lon&&(i=o.lon),void 0!=o.height&&(r=o.height),n=v.wrap90(v.parse(n)),i=v.wrap180(v.parse(i));if(void 0!=e[1]&&(r=e[1]),isNaN(n)||isNaN(i))throw new TypeError("invalid point ‘".concat(JSON.stringify(e[0]),"’"))}if("string"==typeof e[0]&&2==e[0].split(",").length){var h=e[0].split(","),c=Object(d["a"])(h,2);if(n=c[0],i=c[1],n=v.wrap90(v.parse(n)),i=v.wrap180(v.parse(i)),r=e[1]||0,isNaN(n)||isNaN(i))throw new TypeError("invalid point ‘".concat(e[0],"’"))}if(void 0==n&&void 0==i&&(n=e[0],i=e[1],n=v.wrap90(v.parse(n)),i=v.wrap180(v.parse(i)),r=e[2]||0,isNaN(n)||isNaN(i)))throw new TypeError("invalid point ‘".concat(e.toString(),"’"));return new this(n,i,r)}},{key:"ellipsoids",get:function(){return g}},{key:"datums",get:function(){return w}}]),t}(),N=function(t){Object(r["a"])(a,t);var e=Object(o["a"])(a);function a(t,n,i){return Object(s["a"])(this,a),e.call(this,t,n,i)}return Object(h["a"])(a,[{key:"toLatLon",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:g.WGS84;if(!t||!t.a)throw new TypeError("invalid ellipsoid ‘".concat(t,"’"));var e=this.x,a=this.y,n=this.z,i=t.a,r=t.b,o=t.f,s=2*o-o*o,h=s/(1-s),l=Math.sqrt(e*e+a*a),c=Math.sqrt(l*l+n*n),u=r*n/(i*l)*(1+h*r/c),d=u/Math.sqrt(1+u*u),f=d/u,p=isNaN(f)?0:Math.atan2(n+h*r*d*d*d,l-s*i*f*f*f),v=Math.atan2(a,e),m=Math.sin(p),y=Math.cos(p),w=i/Math.sqrt(1-s*m*m),N=l*y+n*m-i*i/w,M=new b(p.toDegrees(),v.toDegrees(),N);return M}},{key:"toString",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0,e=this.x.toFixed(t),a=this.y.toFixed(t),n=this.z.toFixed(t);return"[".concat(e,",").concat(a,",").concat(n,"]")}}]),a}(y),M={WGS84:{a:6378137,b:6356752.314245,f:1/298.257223563},Airy1830:{a:6377563.396,b:6356256.909,f:1/299.3249646},AiryModified:{a:6377340.189,b:6356034.448,f:1/299.3249646},Bessel1841:{a:6377397.155,b:6356078.962818,f:1/299.1528128},Clarke1866:{a:6378206.4,b:6356583.8,f:1/294.978698214},Clarke1880IGN:{a:6378249.2,b:6356515,f:1/293.466021294},GRS80:{a:6378137,b:6356752.31414,f:1/298.257222101},Intl1924:{a:6378388,b:6356911.946,f:1/297},WGS72:{a:6378135,b:6356750.5,f:1/298.26}},k={ED50:{ellipsoid:M.Intl1924,transform:[89.5,93.8,123.1,-1.2,0,0,.156]},ETRS89:{ellipsoid:M.GRS80,transform:[0,0,0,0,0,0,0]},Irl1975:{ellipsoid:M.AiryModified,transform:[-482.53,130.596,-564.557,-8.15,1.042,.214,.631]},NAD27:{ellipsoid:M.Clarke1866,transform:[8,-160,-176,0,0,0,0]},NAD83:{ellipsoid:M.GRS80,transform:[.9956,-1.9103,-.5215,-62e-5,.025915,.009426,.011599]},NTF:{ellipsoid:M.Clarke1880IGN,transform:[168,60,-320,0,0,0,0]},OSGB36:{ellipsoid:M.Airy1830,transform:[-446.448,125.157,-542.06,20.4894,-.1502,-.247,-.8421]},Potsdam:{ellipsoid:M.Bessel1841,transform:[-582,-105,-414,-8.3,1.04,.35,-3.08]},TokyoJapan:{ellipsoid:M.Bessel1841,transform:[148,-507,-685,0,0,0,0]},WGS72:{ellipsoid:M.WGS72,transform:[0,0,-4.5,-.22,0,0,.554]},WGS84:{ellipsoid:M.WGS84,transform:[0,0,0,0,0,0,0]}};Object.keys(M).forEach((function(t){return Object.freeze(M[t])})),Object.keys(k).forEach((function(t){Object.freeze(k[t]),Object.freeze(k[t].transform)}));var x=function(t){Object(r["a"])(a,t);var e=Object(o["a"])(a);function a(t,n){var i,r=arguments.length>2&&void 0!==arguments[2]?arguments[2]:0,o=arguments.length>3&&void 0!==arguments[3]?arguments[3]:k.WGS84;if(Object(s["a"])(this,a),!o||void 0==o.ellipsoid)throw new TypeError("unrecognised datum ‘".concat(o,"’"));return i=e.call(this,t,n,r),i._datum=o,i}return Object(h["a"])(a,[{key:"convertDatum",value:function(t){if(!t||void 0==t.ellipsoid)throw new TypeError("unrecognised datum ‘".concat(t,"’"));var e=this.toCartesian(),a=e.convertDatum(t),n=a.toLatLon();return n}},{key:"toCartesian",value:function(){var t=Object(c["a"])(Object(u["a"])(a.prototype),"toCartesian",this).call(this),e=new S(t.x,t.y,t.z,this.datum);return e}},{key:"datum",get:function(){return this._datum}}],[{key:"parse",value:function(){for(var t,e=k.WGS84,n=arguments.length,i=new Array(n),r=0;r<n;r++)i[r]=arguments[r];if((4==i.length||3==i.length&&"object"==Object(l["a"])(i[2]))&&(e=i.pop()),!e||void 0==e.ellipsoid)throw new TypeError("unrecognised datum ‘".concat(e,"’"));var o=(t=Object(c["a"])(Object(u["a"])(a),"parse",this)).call.apply(t,[this].concat(i));return o._datum=e,o}},{key:"ellipsoids",get:function(){return M}},{key:"datums",get:function(){return k}}]),a}(b),S=function(t){Object(r["a"])(a,t);var e=Object(o["a"])(a);function a(t,n,i){var r,o=arguments.length>3&&void 0!==arguments[3]?arguments[3]:void 0;if(Object(s["a"])(this,a),o&&void 0==o.ellipsoid)throw new TypeError("unrecognised datum ‘".concat(o,"’"));return r=e.call(this,t,n,i),o&&(r._datum=o),r}return Object(h["a"])(a,[{key:"toLatLon",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:void 0;t&&(console.info("datum parameter to Cartesian_Datum.toLatLon is deprecated: set datum before calling toLatLon()"),this.datum=t);var e=this.datum||k.WGS84;if(!e||void 0==e.ellipsoid)throw new TypeError("unrecognised datum ‘".concat(e,"’"));var n=Object(c["a"])(Object(u["a"])(a.prototype),"toLatLon",this).call(this,e.ellipsoid),i=new x(n.lat,n.lon,n.height,this.datum);return i}},{key:"convertDatum",value:function(t){if(!t||void 0==t.ellipsoid)throw new TypeError("unrecognised datum ‘".concat(t,"’"));if(!this.datum)throw new TypeError("cartesian coordinate has no datum");var e=null,a=null;void 0!=this.datum&&this.datum!=k.WGS84||(e=this,a=t.transform),t==k.WGS84&&(e=this,a=this.datum.transform.map((function(t){return-t}))),null==a&&(e=this.convertDatum(k.WGS84),a=t.transform);var n=e.applyTransform(a);return n.datum=t,n}},{key:"applyTransform",value:function(t){var e=this.x,n=this.y,i=this.z,r=t[0],o=t[1],s=t[2],h=t[3]/1e6+1,l=(t[4]/3600).toRadians(),c=(t[5]/3600).toRadians(),u=(t[6]/3600).toRadians(),d=r+e*h-n*u+i*c,f=o+e*u+n*h-i*l,p=s-e*c+n*l+i*h;return new a(d,f,p)}},{key:"datum",get:function(){return this._datum},set:function(t){if(!t||void 0==t.ellipsoid)throw new TypeError("unrecognised datum ‘".concat(t,"’"));this._datum=t}}]),a}(N),E=function(){function t(e,a,n,i){var r=arguments.length>4&&void 0!==arguments[4]?arguments[4]:x.datums.WGS84,o=arguments.length>5&&void 0!==arguments[5]?arguments[5]:null,h=arguments.length>6&&void 0!==arguments[6]?arguments[6]:null,l=!(arguments.length>7&&void 0!==arguments[7])||arguments[7];if(Object(s["a"])(this,t),!(1<=e&&e<=60))throw new RangeError("invalid UTM zone ‘".concat(e,"’"));if(e!=parseInt(e))throw new RangeError("invalid UTM zone ‘".concat(e,"’"));if("string"!=typeof a||!a.match(/[NS]/i))throw new RangeError("invalid UTM hemisphere ‘".concat(a,"’"));if(l){if(!(0<=n&&n<=1e6))throw new RangeError("invalid UTM easting ‘".concat(n,"’"));if("N"==a.toUpperCase()&&!(0<=i&&i<9328094))throw new RangeError("invalid UTM northing ‘".concat(i,"’"));if("S"==a.toUpperCase()&&!(1118414<i&&i<=1e7))throw new RangeError("invalid UTM northing ‘".concat(i,"’"))}if(!r||void 0==r.ellipsoid)throw new TypeError("unrecognised datum ‘".concat(r,"’"));this.zone=Number(e),this.hemisphere=a.toUpperCase(),this.easting=Number(n),this.northing=Number(i),this.datum=r,this.convergence=null===o?null:Number(o),this.scale=null===h?null:Number(h)}return Object(h["a"])(t,[{key:"toLatLon",value:function(){for(var t=this.zone,e=this.hemisphere,a=5e5,n=1e7,i=this.datum.ellipsoid,r=i.a,o=i.f,s=.9996,h=this.easting-a,l="S"==e?this.northing-n:this.northing,c=Math.sqrt(o*(2-o)),u=o/(2-o),d=u*u,f=u*d,p=u*f,v=u*p,m=u*v,y=r/(1+u)*(1+1/4*d+1/64*p+1/256*m),g=h/(s*y),w=l/(s*y),b=[null,.5*u-2/3*d+37/96*f-1/360*p-81/512*v+96199/604800*m,1/48*d+1/15*f-437/1440*p+46/105*v-1118711/3870720*m,17/480*f-37/840*p-209/4480*v+5569/90720*m,4397/161280*p-11/504*v-830251/7257600*m,4583/161280*v-108847/3991680*m,20648693/638668800*m],N=w,M=1;M<=6;M++)N-=b[M]*Math.sin(2*M*w)*Math.cosh(2*M*g);for(var k=g,x=1;x<=6;x++)k-=b[x]*Math.cos(2*x*w)*Math.sinh(2*x*g);var S=Math.sinh(k),E=Math.sin(N),j=Math.cos(N),O=E/Math.sqrt(S*S+j*j),z=null,F=O;do{var W=Math.sinh(c*Math.atanh(c*F/Math.sqrt(1+F*F))),R=F*Math.sqrt(1+W*W)-W*Math.sqrt(1+F*F);z=(O-R)/Math.sqrt(1+R*R)*(1+(1-c*c)*F*F)/((1-c*c)*Math.sqrt(1+F*F)),F+=z}while(Math.abs(z)>1e-12);for(var _=F,L=Math.atan(_),q=Math.atan2(S,j),G=1,A=1;A<=6;A++)G-=2*A*b[A]*Math.cos(2*A*w)*Math.cosh(2*A*g);for(var D=0,I=1;I<=6;I++)D+=2*I*b[I]*Math.sin(2*I*w)*Math.sinh(2*I*g);var U=Math.atan(Math.tan(N)*Math.tanh(k)),C=Math.atan2(D,G),P=U+C,V=Math.sin(L),B=Math.sqrt(1-c*c*V*V)*Math.sqrt(1+_*_)*Math.sqrt(S*S+j*j),X=y/r/Math.sqrt(G*G+D*D),$=s*B*X,J=(6*(t-1)-180+3).toRadians();q+=J;var H=Number(L.toDegrees().toFixed(14)),K=Number(q.toDegrees().toFixed(14)),Q=Number(P.toDegrees().toFixed(9)),Z=Number($.toFixed(12)),Y=new T(H,K,0,this.datum);return Y.convergence=Q,Y.scale=Z,Y}},{key:"toString",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0,e=this.zone.toString().padStart(2,"0"),a=this.hemisphere,n=this.easting.toFixed(t),i=this.northing.toFixed(t);return"".concat(e," ").concat(a," ").concat(n," ").concat(i)}}],[{key:"parse",value:function(t){var e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:x.datums.WGS84;if(t=t.trim().match(/\S+/g),null==t||4!=t.length)throw new Error("invalid UTM coordinate ‘".concat(t,"’"));var a=t[0],n=t[1],i=t[2],r=t[3];return new this(a,n,i,r,e)}}]),t}(),T=function(t){Object(r["a"])(a,t);var e=Object(o["a"])(a);function a(){return Object(s["a"])(this,a),e.apply(this,arguments)}return Object(h["a"])(a,[{key:"toUtm",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:void 0;if(!(-80<=this.lat&&this.lat<=84))throw new RangeError("latitude ‘".concat(this.lat,"’ outside UTM limits"));var e=5e5,a=1e7,n=t||Math.floor((this.lon+180)/6)+1,i=(6*(n-1)-180+3).toRadians(),r="CDEFGHJKLMNPQRSTUVWXX",o=r.charAt(Math.floor(this.lat/8+10));31==n&&"V"==o&&this.lon>=3&&(n++,i+=6..toRadians()),32==n&&"X"==o&&this.lon<9&&(n--,i-=6..toRadians()),32==n&&"X"==o&&this.lon>=9&&(n++,i+=6..toRadians()),34==n&&"X"==o&&this.lon<21&&(n--,i-=6..toRadians()),34==n&&"X"==o&&this.lon>=21&&(n++,i+=6..toRadians()),36==n&&"X"==o&&this.lon<33&&(n--,i-=6..toRadians()),36==n&&"X"==o&&this.lon>=33&&(n++,i+=6..toRadians());for(var s=this.lat.toRadians(),h=this.lon.toRadians()-i,l=this.datum?this.datum.ellipsoid:x.ellipsoids.WGS84,c=l.a,u=l.f,d=.9996,f=Math.sqrt(u*(2-u)),p=u/(2-u),v=p*p,m=p*v,y=p*m,g=p*y,w=p*g,b=Math.cos(h),N=Math.sin(h),M=Math.tan(h),k=Math.tan(s),S=Math.sinh(f*Math.atanh(f*k/Math.sqrt(1+k*k))),T=k*Math.sqrt(1+S*S)-S*Math.sqrt(1+k*k),j=Math.atan2(T,b),O=Math.asinh(N/Math.sqrt(T*T+b*b)),z=c/(1+p)*(1+1/4*v+1/64*y+1/256*w),F=[null,.5*p-2/3*v+5/16*m+41/180*y-127/288*g+7891/37800*w,13/48*v-.6*m+557/1440*y+281/630*g-1983433/1935360*w,61/240*m-103/140*y+15061/26880*g+167603/181440*w,49561/161280*y-179/168*g+6601661/7257600*w,34729/80640*g-3418889/1995840*w,.6650675310896665*w],W=j,R=1;R<=6;R++)W+=F[R]*Math.sin(2*R*j)*Math.cosh(2*R*O);for(var _=O,L=1;L<=6;L++)_+=F[L]*Math.cos(2*L*j)*Math.sinh(2*L*O);for(var q=d*z*_,G=d*z*W,A=1,D=1;D<=6;D++)A+=2*D*F[D]*Math.cos(2*D*j)*Math.cosh(2*D*O);for(var I=0,U=1;U<=6;U++)I+=2*U*F[U]*Math.sin(2*U*j)*Math.sinh(2*U*O);var C=Math.atan(T/Math.sqrt(1+T*T)*M),P=Math.atan2(I,A),V=C+P,B=Math.sin(s),X=Math.sqrt(1-f*f*B*B)*Math.sqrt(1+k*k)/Math.sqrt(T*T+b*b),$=z/c*Math.sqrt(A*A+I*I),J=d*X*$;q+=e,G<0&&(G+=a),q=Number(q.toFixed(9)),G=Number(G.toFixed(9));var H=Number(V.toDegrees().toFixed(9)),K=Number(J.toFixed(12)),Q=this.lat>=0?"N":"S";return new E(n,Q,q,G,this.datum,H,K,!!t)}}]),a}(x),j=null,O=null,z={name:"TraceMonitor",props:{cId:{type:String,required:!1}},data:function(){return{channel:null,winHeight:window.innerHeight,winWidth:window.innerWidth}},created:function(){var t=this.cId?this.cId:this.$route.params.cid;t&&(this.channel=new BroadcastChannel(t),this.channel.onmessage=this.onMessage)},mounted:function(){},methods:{registry:function(){this.channel&&this.channel.postMessage({type:"registry",data:{}})},onMessage:function(t){var e=t.data.type,a=t.data.data;switch(e){case"trace-lines":this.createTraceChart(a);break;case"location":this.addLocation(a);break}},createTraceChart:function(t){this.$nextTick((function(){if(t){j&&j.dispose(),j=echarts.init(document.getElementById("trace-monitor"));var e=null,a=null,n=null,i=null,r=t.map((function(t){var r=new T(t[1],t[0]),o=r.toUtm(),s=o.toString(3).split(" ").slice(2).map((function(t){return parseFloat(t)})),h=s[0],l=s[1];return e&&a&&i&&n||(e=h,a=h,i=l,n=l),a=Math.max.apply(null,[a,h]),e=Math.min.apply(null,[e,h]),n=Math.min.apply(null,[n,l]),i=Math.max.apply(null,[i,l]),s.push(t[1],t[0]),s})),o=["#5793f3","#d14a61","#675bba"];O={color:o,tooltip:{trigger:"item",formatter:function(t){var e=t["data"];return"经度："+e[2]+"，纬度："+e[3]}},legend:{data:["寻迹路线","行驶路线","预测路线"]},xAxis:{type:"value",splitLine:{show:!1},min:e/2,max:2*a},yAxis:{type:"value",splitLine:{show:!1},min:n/2,max:2*i},dataZoom:[{show:!0,type:"inside",filterMode:"none",xAxisIndex:[0],startValue:e,endValue:a},{show:!0,type:"inside",filterMode:"none",yAxisIndex:[0],startValue:n,endValue:i}],graphic:{type:"image",id:"logo",right:40,top:20,z:-10,bounding:"raw",origin:[75,75],style:{image:"https://s1.ax1x.com/2020/08/13/dpghRS.png",width:150,height:150,opacity:.6}},series:[{name:"寻迹路线",type:"line",hoverAnimation:!1,data:r,lineStyle:{width:4}},{name:"行驶路线",type:"line",showSymbol:!1,showAllSymbol:!1,hoverAnimation:!1,data:[],lineStyle:{},markPoint:{symbol:"image://https://webapi.amap.com/images/car.png",symbolRotate:0,symbolSize:30,data:[{name:"当前位置",xAxis:0,yAxis:0}]}},{name:"预测路线",type:"line",showSymbol:!0,hoverAnimation:!1,data:[],lineStyle:{width:2,type:"dashed"}}]},j.setOption(O),j.resize()}}))},addLocation:function(t){if(j){var e=[parseFloat(t["lng"]),parseFloat(t["lat"])],a=this.transformUtm(e);if(a.push(t["lng"],t["lat"]),O.series[1]["data"].push(a),O.series[1]["markPoint"]["symbolRotate"]=90-t["angle"],O.series[1]["markPoint"]["data"][0]["xAxis"]=a[0],O.series[1]["markPoint"]["data"][0]["yAxis"]=a[1],t["plat"]&&t["plng"]){var n=[parseFloat(t["plng"]),parseFloat(t["plat"])],i=this.transformUtm(n);i.push(t["plng"],t["plat"]),O.series[2]["data"]=[a,i]}j.setOption({series:O.series})}},transformUtm:function(t){var e=new T(t[1],t[0]),a=e.toUtm();return a.toString(3).split(" ").slice(2).map((function(t){return parseFloat(t)}))},maximize:function(){window.open("#/expand/trace/"+this.cId)}}},F=z,W=a("2877"),R=Object(W["a"])(F,n,i,!1,null,"3ce92b4c",null);e["default"]=R.exports}}]);