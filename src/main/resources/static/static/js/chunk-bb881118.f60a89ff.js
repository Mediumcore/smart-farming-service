(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-bb881118"],{"1c18":function(e,t,i){},"333d":function(e,t,i){"use strict";var n=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"pagination-container",class:{hidden:e.hidden}},[i("el-pagination",e._b({attrs:{background:e.background,"current-page":e.currentPage,"page-size":e.pageSize,layout:e.layout,"page-sizes":e.pageSizes,total:e.total},on:{"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t},"update:pageSize":function(t){e.pageSize=t},"update:page-size":function(t){e.pageSize=t},"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}},"el-pagination",e.$attrs,!1))],1)},a=[];i("a9e3");Math.easeInOutQuad=function(e,t,i,n){return e/=n/2,e<1?i/2*e*e+t:(e--,-i/2*(e*(e-2)-1)+t)};var o=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(e){window.setTimeout(e,1e3/60)}}();function r(e){document.documentElement.scrollTop=e,document.body.parentNode.scrollTop=e,document.body.scrollTop=e}function c(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function l(e,t,i){var n=c(),a=e-n,l=20,s=0;t="undefined"===typeof t?500:t;var d=function e(){s+=l;var c=Math.easeInOutQuad(s,n,a,t);r(c),s<t?o(e):i&&"function"===typeof i&&i()};d()}var s={name:"Pagination",props:{total:{required:!0,type:Number},page:{type:Number,default:1},limit:{type:Number,default:20},pageSizes:{type:Array,default:function(){return[10,20,30,50]}},layout:{type:String,default:"total, sizes, prev, pager, next, jumper"},background:{type:Boolean,default:!0},autoScroll:{type:Boolean,default:!0},hidden:{type:Boolean,default:!1}},computed:{currentPage:{get:function(){return this.page},set:function(e){this.$emit("update:page",e)}},pageSize:{get:function(){return this.limit},set:function(e){this.$emit("update:limit",e)}}},methods:{handleSizeChange:function(e){this.$emit("pagination",{page:this.currentPage,limit:e}),this.autoScroll&&l(0,800)},handleCurrentChange:function(e){this.$emit("pagination",{page:e,limit:this.pageSize}),this.autoScroll&&l(0,800)}}},d=s,u=(i("e498"),i("2877")),p=Object(u["a"])(d,n,a,!1,null,"6af373ef",null);t["a"]=p.exports},3427:function(e,t,i){"use strict";i.r(t);var n=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"dashboard-editor-container"},[i("el-row",{staticStyle:{background:"#fff",padding:"16px 16px 0","margin-bottom":"32px"}},[i("device-table")],1)],1)},a=[],o=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",[i("el-button",{staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-plus"},on:{click:e.handleAddDialog}},[e._v(" 添加设备 ")]),i("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],staticStyle:{width:"100%","padding-top":"15px"},attrs:{data:e.list}},[i("el-table-column",{attrs:{label:"ID","min-width":"40"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.id)+" ")]}}])}),i("el-table-column",{attrs:{label:"设备名称","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.name)+" ")]}}])}),i("el-table-column",{attrs:{label:"设备类型","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.type.label)+" ")]}}])}),i("el-table-column",{attrs:{label:"设备描述","min-width":"150"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.description)+" ")]}}])}),i("el-table-column",{attrs:{label:"设备IP","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.ipaddress)+" ")]}}])}),i("el-table-column",{attrs:{label:"上线时间","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[null!==t.row.onlineTime?i("span",[e._v(e._s(e.$moment(t.row.onlineTime).format("YYYY-MM-DD HH:mm:ss")))]):e._e()]}}])}),i("el-table-column",{attrs:{label:"下线时间","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[null!==t.row.offlineTime?i("span",[e._v(e._s(e.$moment(t.row.offlineTime).format("YYYY-MM-DD HH:mm:ss")))]):e._e()]}}])}),i("el-table-column",{attrs:{label:"操作","min-width":"180"},scopedSlots:e._u([{key:"default",fn:function(t){return[i("el-button",{attrs:{size:"small"},on:{click:function(i){return e.handleViewCert(t.row.id)}}},[e._v("凭证")]),i("el-button",{attrs:{type:"primary",size:"small"},on:{click:function(i){return e.handleEdit(t.row)}}},[e._v("编辑")]),i("el-button",{attrs:{type:"danger",size:"small"},on:{click:function(i){return e.handleDelete(t.row.id)}}},[e._v("删除")])]}}])})],1),i("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.listQuery.page,limit:e.listQuery.limit},on:{"update:page":function(t){return e.$set(e.listQuery,"page",t)},"update:limit":function(t){return e.$set(e.listQuery,"limit",t)},pagination:e.fetchDevices}}),i("el-dialog",{attrs:{title:e.isDeviceEdit?"修改设备":"添加设备",visible:e.dialogDeviceEditVisible},on:{"update:visible":function(t){e.dialogDeviceEditVisible=t}}},[i("el-form",{ref:"deviceEditForm",attrs:{model:e.deviceEditForm,rules:e.deviceEditFormRules,"label-width":"100px"}},[i("el-form-item",{attrs:{label:"设备名称",prop:"name"}},[i("el-input",{model:{value:e.deviceEditForm.name,callback:function(t){e.$set(e.deviceEditForm,"name",t)},expression:"deviceEditForm.name"}})],1),i("el-form-item",{attrs:{label:"设备类型",prop:"type"}},[i("el-select",{attrs:{placeholder:"请选择设备类型",disabled:e.isDeviceEdit},model:{value:e.deviceEditForm.type,callback:function(t){e.$set(e.deviceEditForm,"type",t)},expression:"deviceEditForm.type"}},e._l(e.deviceTypes,(function(t){return i("el-option",{key:t.id,attrs:{label:t.label,value:t.type}},[i("span",[e._v(e._s(t.label))])])})),1)],1),i("el-form-item",{attrs:{label:"设备描述"}},[i("el-input",{model:{value:e.deviceEditForm.desc,callback:function(t){e.$set(e.deviceEditForm,"desc",t)},expression:"deviceEditForm.desc"}})],1)],1),i("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:function(t){e.dialogDeviceEditVisible=!1}}},[e._v("取 消")]),i("el-button",{attrs:{type:"primary"},on:{click:e.handleDeviceSave}},[e._v("确 定")])],1)],1),i("el-dialog",{attrs:{title:"设备凭证",visible:e.dialogCertVisible},on:{"update:visible":function(t){e.dialogCertVisible=t}}},[e._v(" "+e._s(e.certificate)+" ")])],1)},r=[],c=(i("a4d3"),i("e01a"),i("b0c0"),i("d3b7"),i("333d")),l=i("a692"),s={components:{Pagination:c["a"]},data:function(){return{list:null,total:10,listLoading:!0,listQuery:{page:1,limit:10},deviceTypes:[],dialogDeviceEditVisible:!1,dialogCertVisible:!1,certificate:"",isDeviceEdit:!1,deviceEditForm:{id:"",name:"",type:"",desc:""},deviceEditFormRules:{name:[{required:!0,message:"请输入设备名称",trigger:"blur"}],type:[{required:!0,message:"请选择设备类型",trigger:"blur"}]}}},created:function(){this.fetchDevices(),this.fetchDeviceTypes()},methods:{fetchDevices:function(){var e=this;this.listLoading=!0,Object(l["f"])(this.listQuery.page,this.listQuery.limit).then((function(t){var i=t["data"];e.list=i["list"],e.total=i["total"]})).finally((function(){e.listLoading=!1}))},fetchDeviceTypes:function(){var e=this;Object(l["e"])().then((function(t){e.deviceTypes=t["data"]["list"]}))},handleAddDialog:function(){this.isDeviceEdit=!1,this.deviceEditForm.desc="",this.deviceEditForm.name="",this.deviceEditForm.type=this.deviceTypes.length>0?this.deviceTypes[0]["type"]:"",this.dialogDeviceEditVisible=!0},handleViewCert:function(e){var t=this;Object(l["c"])(e).then((function(e){t.certificate=e["data"]["ciphertext"],t.dialogCertVisible=!0}))},handleEdit:function(e){this.isDeviceEdit=!0,this.deviceEditForm.name=e.name,this.deviceEditForm.desc=e.description,this.deviceEditForm.type=e.type.type,this.deviceEditForm.id=e.id,this.dialogDeviceEditVisible=!0},handleDeviceSave:function(){var e=this;this.$refs["deviceEditForm"].validate((function(t){t&&(e.isDeviceEdit?Object(l["b"])(e.deviceEditForm.id,e.deviceEditForm.name,e.deviceEditForm.desc,e.deviceEditForm.type).then((function(t){e.fetchDevices(),e.dialogDeviceEditVisible=!1})):Object(l["a"])(e.deviceEditForm.name,e.deviceEditForm.desc,e.deviceEditForm.type).then((function(t){e.fetchDevices(),e.dialogDeviceEditVisible=!1})))}))},handleDelete:function(e){var t=this;this.$confirm("此操作将永久删除该设备，并清除该设备的所有事件及指令, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){Object(l["g"])(e).then((function(e){t.fetchDevices(),t.$message({type:"success",message:"删除成功!"})}))})).catch((function(){}))}}},d=s,u=i("2877"),p=Object(u["a"])(d,o,r,!1,null,null,null),m=p.exports,f={name:"DeviceManagement",components:{DeviceTable:m}},v=f,g=Object(u["a"])(v,n,a,!1,null,null,null);t["default"]=g.exports},a692:function(e,t,i){"use strict";i.d(t,"d",(function(){return a})),i.d(t,"f",(function(){return o})),i.d(t,"a",(function(){return r})),i.d(t,"b",(function(){return c})),i.d(t,"g",(function(){return l})),i.d(t,"e",(function(){return s})),i.d(t,"c",(function(){return d}));var n=i("b775");function a(e,t){return Object(n["a"])({url:"/api/v1/devicemgr/devices/records",method:"get",params:{pageNum:e,pageSize:t}})}function o(e,t,i,a){return Object(n["a"])({url:"/api/v1/devicemgr/devices",method:"get",params:{pageNum:e,pageSize:t,deviceName:i,deviceType:a}})}function r(e,t,i){return Object(n["a"])({url:"/api/v1/devicemgr/devices",method:"post",params:{name:e,description:t,type:i}})}function c(e,t,i,a){return Object(n["a"])({url:"/api/v1/devicemgr/devices/"+e,method:"put",params:{name:t,description:i,type:a}})}function l(e){return Object(n["a"])({url:"/api/v1/devicemgr/devices/"+e,method:"delete"})}function s(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:1,t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:100;return Object(n["a"])({url:"/api/v1/devicemgr/types",method:"get",params:{pageNum:e,pageSize:t}})}function d(e){return Object(n["a"])({url:"/api/v1/devicemgr/devices/"+e+"/certificate",method:"get"})}},a9e3:function(e,t,i){"use strict";var n=i("83ab"),a=i("da84"),o=i("94ca"),r=i("6eeb"),c=i("5135"),l=i("c6b6"),s=i("7156"),d=i("c04e"),u=i("d039"),p=i("7c73"),m=i("241c").f,f=i("06cf").f,v=i("9bf2").f,g=i("58a8").trim,h="Number",b=a[h],y=b.prototype,E=l(p(y))==h,_=function(e){var t,i,n,a,o,r,c,l,s=d(e,!1);if("string"==typeof s&&s.length>2)if(s=g(s),t=s.charCodeAt(0),43===t||45===t){if(i=s.charCodeAt(2),88===i||120===i)return NaN}else if(48===t){switch(s.charCodeAt(1)){case 66:case 98:n=2,a=49;break;case 79:case 111:n=8,a=55;break;default:return+s}for(o=s.slice(2),r=o.length,c=0;c<r;c++)if(l=o.charCodeAt(c),l<48||l>a)return NaN;return parseInt(o,n)}return+s};if(o(h,!b(" 0o1")||!b("0b1")||b("+0x1"))){for(var w,F=function(e){var t=arguments.length<1?0:e,i=this;return i instanceof F&&(E?u((function(){y.valueOf.call(i)})):l(i)!=h)?s(new b(_(t)),i,F):_(t)},D=n?m(b):"MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,EPSILON,isFinite,isInteger,isNaN,isSafeInteger,MAX_SAFE_INTEGER,MIN_SAFE_INTEGER,parseFloat,parseInt,isInteger".split(","),S=0;D.length>S;S++)c(b,w=D[S])&&!c(F,w)&&v(F,w,f(b,w));F.prototype=y,y.constructor=F,r(a,h,F)}},e498:function(e,t,i){"use strict";var n=i("1c18"),a=i.n(n);a.a}}]);