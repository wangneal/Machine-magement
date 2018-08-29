$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/machines/list',
        datatype: "json",
        colModel: [			
			{ label: '序号', name: 'machinesId', index: 'machines_id', width: 50, key: true },
			{ label: '设备名称', name: 'machinesName', index: 'machines_name', width: 80 }, 			
			{ label: '设备编号', name: 'machinesType', index: 'machines_type', width: 80 }, 			
			{ label: '设备状态', name: 'machinesStatus', index: 'machines_status', width: 80,
                formatter: function(value, options, row){
                    if (value < 1){
                        return  '<span class="label label-success">正常</span>';
					} else if (value > 1){
                        return '<span class="label label-danger">已报废</span>' ;
					} else {
                        return '<span class="label label-warning">待维修</span>' ;
					}


                }},
			{ label: '设备分类', name: 'machinesCategory', index: 'machines_category', width: 80 },
			{ label: '设备所在仓位', name: 'repoName', width: 80 }
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

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "reposId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"//不是url，不需跳转等操作
        }
    }
};
//仓库树
var ztree;

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            machineName: null
        },
		showList: true,
		title: null,
		machines: {},
        repoList: {},
        repos: {
            parentName: null,
            reposName: null,
            parentId: 0
        }
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.machines = {};
            vm.repos = {reposName: null, parentId: 0};
			//获取仓库信息
			this.getReposTree();
			this.getRepos();

		},
		update: function (event) {
			var machinesId = getSelectedRow();
			if(machinesId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.repos = {reposName: null, parentId: 0};
            vm.getInfo(machinesId);
            //获取仓库信息
            this.getReposTree();
            this.getRepos();
		},
		saveOrUpdate: function (event) {
            var node = ztree.getSelectedNodes();
            //选择上级部门
            vm.machines.repoName = node[0].reposName;
            vm.machines.repoId = node[0].reposId;
			var url = vm.machines.machinesId == null ? "sys/machines/save" : "sys/machines/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.machines),
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
        getRepos:function(){
		    $.get(baseURL + "sys/repos/select", function (r) {
                vm.repoList = r.list;
            });
        },
        getReposTree: function(){
            //加载仓库树
            $.get(baseURL + "sys/repos/select", function(r){
                //初始化ztree
                $.fn.zTree.init($("#reposTree"), setting, r.reposList);
                ztree = $.fn.zTree.getZTreeObj("reposTree");//debug
                //查找parentId=0的节点
                console.log(vm.repos.parentId);
                var node = ztree.getNodeByParam("reposId", vm.repos.parentId);
                var nodes = ztree.getNodes();
                if (node != null){
                    ztree.selectNode(node);
                    vm.repos.parentName = node.name;
                }

            })
        },
		del: function (event) {
			var machinesIds = getSelectedRows();
			if(machinesIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/machines/delete",
                    contentType: "application/json",
				    data: JSON.stringify(machinesIds),
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
        reposTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择仓库",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#reposLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.repos.parentId = node[0].reposId;
                    vm.repos.parentName = node[0].reposName;
                    layer.close(index);
                }
            });
        },
		getInfo: function(machinesId){
			$.get(baseURL + "sys/machines/info/"+machinesId, function(r){
                vm.machines = r.machines;
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