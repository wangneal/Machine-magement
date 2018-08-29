$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/order/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '设备名称', name: 'machineName',  width: 80 },
            { label: '设备编号', name: 'machineType',  width: 80 },
            { label: '设备状态', name: 'machineStatus',  width: 80 ,
                formatter: function(value1, options1, row1){
                    if (value1 < 1){

                        return  '<span class="label label-success">正常</span>';
                    } else if (value1 > 1){
                        return '<span class="label label-danger">已报废</span>' ;
                    } else {
                        return '<span class="label label-warning">待维修</span>' ;
                    }


                }},
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '归还时间', name: 'lastTime', index: 'last_time', width: 80 },
			{ label: '状态', name: 'status', index: 'status', width: 80,formatter:function (value2, opthion2, row2) {
					return value2 === 0 ? '<span class="label label-danger">未归还</span>':'<span class="label label-success">已归还，订单关闭</span>';
                } },
			{ label: '借用人', name: 'userName', index: 'u_id', width: 80 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            machineName: null
        },
		showList: true,
		title: null,
		order: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.order = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.order.id == null ? "sys/order/save" : "sys/order/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.order),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		// TODO 选定数据导出excel 此方法未实现
		excel:function(event){
			var ids = getSelectedRows();
			if (ids == null){
				return ;
			}
			confirm('确定仅导出所选部分的数据吗？', function () {
				$.ajax({
					type: "GET",
					url: baseURL + "/sys/order/excel",
					contentType: "application/json",
					data: JSON.stringify(ids),
					success: function (r) {
						if (r.code == 0){
							alert("操作成功", function (index) {
								$("#jqGrid").treegrid("reloadGrid");
                            });
						} else {
							alert(r.msg);
						}
                    }
				})
            })
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/order/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "sys/order/info/"+id, function(r){
                vm.order = r.order;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'machineName': vm.q.machineName},
                page:page
            }).trigger("reloadGrid");
		}
	}
});