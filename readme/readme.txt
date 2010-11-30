物料中的查找用 ajax 实现 
会返回material_search_result.jsp,生成的内容 ，其只是一个html片段，利用jquery 的div.html(text); 方法，插入到当前 页面中去

jquery 中处理checkbox      var checkboxs= $(":checkbox[name='m.id'][checked='true']") ; 选中那些 被选 的checkbox 
入了字符过滤


相对于drp4 进行了又一次 重构， 
将 
 创建了视图v_terminalType v_distribType ,
 DistribType TerminalType 不再继承在data_dict
 而是从视图 v_terminalType v_distribType 得来，但两视图的数据仍然存在表data_dict 中
 

