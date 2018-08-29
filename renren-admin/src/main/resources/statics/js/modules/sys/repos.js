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
var ztree;

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		repos: {
			reposName: null,
			parentId: 0
		}
	},
	methods: {
        getReposTree: function(){
            //加载仓库树
            $.get(baseURL + "sys/repos/select", function(r){
                ztree = $.fn.zTree.init($("#reposTree"), setting, r.reposList);
                var node = ztree.getNodeByParam("reposId", vm.repos.parentId);
                ztree.selectNode(node);
                vm.repos.reposName = node.name;
            })
        },
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.repos = {reposName: null, parentId: 0};
			vm.getReposTree();
		},
		update: function (event) {
			var reposId = getReposId();
			if(reposId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(reposId)
		},
		saveOrUpdate: function (event) {
			var url = vm.repos.reposId == null ? "sys/repos/save" : "sys/repos/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.repos),
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
		del: function (event) {
			var reposIds = getReposId();
			if(reposIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/repos/delete",
                    contentType: "application/json",
				    data: JSON.stringify(reposIds),
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
		getInfo: function(reposId){
			$.get(baseURL + "sys/repos/info/"+reposId, function(r){
                vm.repos = r.repos;
                vm.getReposTree();
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
                    //选择上级仓库
                    vm.repos.parentId = node[0].reposId;
                    vm.repos.parentName = node[0].reposName;
                    layer.close(index);
                }
            });
        },
		reload: function (event) {
			vm.showList = true;
			Repo.table.refresh();
		}
	}
});
var Repo = {
    id: "repoTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Repo.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '序号', field: 'reposId', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '仓位名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '仓库名称', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'}]

    return columns;
};


function getReposId () {
    var selected = $('#repoTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}


$(function () {
    $.get(baseURL + "sys/repos/info", function(r){
        var colunms = Repo.initColumn();
        var table = new TreeTable(Repo.id, baseURL + "sys/repos/list", colunms);
        table.setRootCodeValue(r.reposId);
        table.setExpandColumn(2);
        table.setIdField("reposId");
        table.setCodeField("reposId");
        table.setParentCodeField("parentId");
        table.setExpandAll(false);
        table.init();
        Repo.table = table;
    });
});
