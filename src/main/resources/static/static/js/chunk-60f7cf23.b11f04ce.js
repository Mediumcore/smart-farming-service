(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-60f7cf23"],{"1c18":function(e,t,n){},"1fbd":function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"dashboard-editor-container"},[n("el-row",{staticStyle:{background:"#fff",padding:"16px 16px 0","margin-bottom":"32px"}},[n("command-table")],1)],1)},i=[],r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("el-form",{ref:"queryForm",attrs:{inline:"",model:e.listQuery,"label-width":"100px"}},[n("el-form-item",{attrs:{label:"设备名称",prop:"name"}},[n("el-select",{attrs:{loading:e.deviceLoading,filterable:"",remote:"","reserve-keyword":"","remote-method":e.fetchDevices,placeholder:"请选择设备"},model:{value:e.listQuery.name,callback:function(t){e.$set(e.listQuery,"name",t)},expression:"listQuery.name"}},e._l(e.devices,(function(t){return n("el-option",{key:t.id,attrs:{label:t.name,value:t.id}},[n("span",[e._v(e._s(t.name))])])})),1)],1),n("el-form-item",{attrs:{label:"指令类型",prop:"type"}},[n("el-select",{attrs:{placeholder:"请选择指令类型",loading:e.commandTypeLoading},on:{focus:e.fetchCommandTypes},model:{value:e.listQuery.type,callback:function(t){e.$set(e.listQuery,"type",t)},expression:"listQuery.type"}},e._l(e.commandTypes,(function(t){return n("el-option",{key:t.id,attrs:{label:t.label,value:t.id}},[n("span",[e._v(e._s(t.label))])])})),1)],1),n("el-form-item",{attrs:{label:"时间范围",prop:"rangeTime"}},[n("el-date-picker",{attrs:{pickerOptions:e.pickerOptions,type:"datetimerange","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期"},model:{value:e.listQuery.rangeTime,callback:function(t){e.$set(e.listQuery,"rangeTime",t)},expression:"listQuery.rangeTime"}})],1),n("el-form-item",[n("el-button",{on:{click:e.handleFormReset}},[e._v("重置")]),n("el-button",{attrs:{type:"primary"},on:{click:e.fetchCommands}},[e._v("查询")])],1)],1),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],staticStyle:{width:"100%","padding-top":"15px"},attrs:{data:e.list}},[n("el-table-column",{attrs:{label:"ID","min-width":"40"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.id)+" ")]}}])}),n("el-table-column",{attrs:{label:"指令类型","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.type.label)+" ")]}}])}),n("el-table-column",{attrs:{label:"指令内容","min-width":"100","show-overflow-tooltip":"“true”"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.body)+" ")]}}])}),n("el-table-column",{attrs:{label:"所属设备","min-width":"150"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.device.name)+" ")]}}])}),n("el-table-column",{attrs:{label:"产生时间","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[null!==t.row.created?n("span",[e._v(e._s(e.$moment(t.row.created).format("YYYY-MM-DD HH:mm:ss")))]):e._e()]}}])})],1),n("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.listQuery.page,limit:e.listQuery.limit},on:{"update:page":function(t){return e.$set(e.listQuery,"page",t)},"update:limit":function(t){return e.$set(e.listQuery,"limit",t)},pagination:e.fetchCommands}})],1)},o=[],l=(n("b0c0"),n("d3b7"),n("333d")),c=n("a692"),u=n("b775");function s(e,t,n,a,i,r){return Object(u["a"])({url:"/api/v1/devicemgr/commands",method:"get",params:{deviceId:e,typeId:t,startTime:n,endTime:a,pageNum:i,pageSize:r}})}function d(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:1,t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:100;return Object(u["a"])({url:"/api/v1/devicemgr/command/types",method:"get",params:{pageNum:e,pageSize:t}})}var m={name:"CommandTable",components:{Pagination:l["a"]},data:function(){return{list:null,total:10,listLoading:!0,listQuery:{rangeTime:"",page:1,limit:10},deviceLoading:!1,commandTypeLoading:!1,devices:[],commandTypes:[],pickerOptions:{shortcuts:[{text:"最近一天",onClick:function(e){var t=new Date,n=new Date;n.setTime(n.getTime()-864e5),e.$emit("pick",[n,t])}},{text:"最近一个周",onClick:function(e){var t=new Date,n=new Date;n.setTime(n.getTime()-6048e5),e.$emit("pick",[n,t])}}]}}},created:function(){this.fetchCommands()},methods:{fetchCommands:function(){var e=this;this.listLoading=!0;var t,n=null;this.listQuery.rangeTime.length>1&&(t=this.listQuery.rangeTime[0].getTime(),n=this.listQuery.rangeTime[1].getTime()),s(this.listQuery.name,this.listQuery.type,t,n,this.listQuery.page,this.listQuery.limit).then((function(t){var n=t["data"];e.list=n["list"],e.total=n["total"]})).finally((function(){e.listLoading=!1}))},fetchCommandTypes:function(){var e=this;this.commandTypes.length>0||(this.commandTypeLoading=!0,d().then((function(t){e.commandTypes=t["data"]["list"],e.commandTypeLoading=!1})))},fetchDevices:function(e){var t=this;""!==e&&(this.deviceLoading=!0,Object(c["f"])(1,20,e,"").then((function(e){t.devices=e["data"]["list"],t.deviceLoading=!1})))},handleFormReset:function(){this.$refs["queryForm"].resetFields()}}},p=m,f=n("2877"),g=Object(f["a"])(p,r,o,!1,null,"442ebad2",null),h=g.exports,v={name:"CommandManagement",components:{CommandTable:h}},y=v,b=Object(f["a"])(y,a,i,!1,null,"0bf619d5",null);t["default"]=b.exports},"333d":function(e,t,n){"use strict";var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"pagination-container",class:{hidden:e.hidden}},[n("el-pagination",e._b({attrs:{background:e.background,"current-page":e.currentPage,"page-size":e.pageSize,layout:e.layout,"page-sizes":e.pageSizes,total:e.total},on:{"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t},"update:pageSize":function(t){e.pageSize=t},"update:page-size":function(t){e.pageSize=t},"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}},"el-pagination",e.$attrs,!1))],1)},i=[];n("a9e3");Math.easeInOutQuad=function(e,t,n,a){return e/=a/2,e<1?n/2*e*e+t:(e--,-n/2*(e*(e-2)-1)+t)};var r=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(e){window.setTimeout(e,1e3/60)}}();function o(e){document.documentElement.scrollTop=e,document.body.parentNode.scrollTop=e,document.body.scrollTop=e}function l(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function c(e,t,n){var a=l(),i=e-a,c=20,u=0;t="undefined"===typeof t?500:t;var s=function e(){u+=c;var l=Math.easeInOutQuad(u,a,i,t);o(l),u<t?r(e):n&&"function"===typeof n&&n()};s()}var u={name:"Pagination",props:{total:{required:!0,type:Number},page:{type:Number,default:1},limit:{type:Number,default:20},pageSizes:{type:Array,default:function(){return[10,20,30,50]}},layout:{type:String,default:"total, sizes, prev, pager, next, jumper"},background:{type:Boolean,default:!0},autoScroll:{type:Boolean,default:!0},hidden:{type:Boolean,default:!1}},computed:{currentPage:{get:function(){return this.page},set:function(e){this.$emit("update:page",e)}},pageSize:{get:function(){return this.limit},set:function(e){this.$emit("update:limit",e)}}},methods:{handleSizeChange:function(e){this.$emit("pagination",{page:this.currentPage,limit:e}),this.autoScroll&&c(0,800)},handleCurrentChange:function(e){this.$emit("pagination",{page:e,limit:this.pageSize}),this.autoScroll&&c(0,800)}}},s=u,d=(n("e498"),n("2877")),m=Object(d["a"])(s,a,i,!1,null,"6af373ef",null);t["a"]=m.exports},a692:function(e,t,n){"use strict";n.d(t,"d",(function(){return i})),n.d(t,"f",(function(){return r})),n.d(t,"a",(function(){return o})),n.d(t,"b",(function(){return l})),n.d(t,"g",(function(){return c})),n.d(t,"e",(function(){return u})),n.d(t,"c",(function(){return s}));var a=n("b775");function i(e,t){return Object(a["a"])({url:"/api/v1/devicemgr/devices/records",method:"get",params:{pageNum:e,pageSize:t}})}function r(e,t,n,i){return Object(a["a"])({url:"/api/v1/devicemgr/devices",method:"get",params:{pageNum:e,pageSize:t,deviceName:n,deviceType:i}})}function o(e,t,n){return Object(a["a"])({url:"/api/v1/devicemgr/devices",method:"post",params:{name:e,description:t,type:n}})}function l(e,t,n,i){return Object(a["a"])({url:"/api/v1/devicemgr/devices/"+e,method:"put",params:{name:t,description:n,type:i}})}function c(e){return Object(a["a"])({url:"/api/v1/devicemgr/devices/"+e,method:"delete"})}function u(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:1,t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:100;return Object(a["a"])({url:"/api/v1/devicemgr/types",method:"get",params:{pageNum:e,pageSize:t}})}function s(e){return Object(a["a"])({url:"/api/v1/devicemgr/devices/"+e+"/certificate",method:"get"})}},a9e3:function(e,t,n){"use strict";var a=n("83ab"),i=n("da84"),r=n("94ca"),o=n("6eeb"),l=n("5135"),c=n("c6b6"),u=n("7156"),s=n("c04e"),d=n("d039"),m=n("7c73"),p=n("241c").f,f=n("06cf").f,g=n("9bf2").f,h=n("58a8").trim,v="Number",y=i[v],b=y.prototype,w=c(m(b))==v,T=function(e){var t,n,a,i,r,o,l,c,u=s(e,!1);if("string"==typeof u&&u.length>2)if(u=h(u),t=u.charCodeAt(0),43===t||45===t){if(n=u.charCodeAt(2),88===n||120===n)return NaN}else if(48===t){switch(u.charCodeAt(1)){case 66:case 98:a=2,i=49;break;case 79:case 111:a=8,i=55;break;default:return+u}for(r=u.slice(2),o=r.length,l=0;l<o;l++)if(c=r.charCodeAt(l),c<48||c>i)return NaN;return parseInt(r,a)}return+u};if(r(v,!y(" 0o1")||!y("0b1")||y("+0x1"))){for(var _,N=function(e){var t=arguments.length<1?0:e,n=this;return n instanceof N&&(w?d((function(){b.valueOf.call(n)})):c(n)!=v)?u(new y(T(t)),n,N):T(t)},k=a?p(y):"MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,EPSILON,isFinite,isInteger,isNaN,isSafeInteger,MAX_SAFE_INTEGER,MIN_SAFE_INTEGER,parseFloat,parseInt,isInteger".split(","),S=0;k.length>S;S++)l(y,_=k[S])&&!l(N,_)&&g(N,_,f(y,_));N.prototype=b,b.constructor=N,o(i,v,N)}},e498:function(e,t,n){"use strict";var a=n("1c18"),i=n.n(a);i.a}}]);