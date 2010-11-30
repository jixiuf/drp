// Node object 
function Node(id, pid, name, url, title, target, icon, iconOpen, open) {
	this.id = id;
	this.pid = pid;
	this.name = name;//< a  > name</a>
	this.url = url;//  <a href='url' >
	this.title = title;//title is <a href='' title='' target='parent' >
	this.target = target;//target is <a href='' target='parent' >
	this.icon = icon;// dTree.icon['']
	this.iconOpen = iconOpen;// dTree.icon[''] the icon when the node is open 
	this._io = open || false;// io means whether this tree  isOpen   
	this._is = false;//isSElected
	this._ls = false;// is this lastSibling  among brothers
	this._hc = false;// hasChild
	this._ai = 0;
	this._p;
};

// Tree object
function dTree(objName) {
	// 可以在此处配置一下树的参数，
	this.config = {
		target					: null,// <a target=null></a>
		folderLinks			: true,// if true then when you click on a folder it link to a html ,or it will open/close the children of the node
		useSelection		: true,
		useCookies			: true,
		useLines				: true,
		useIcons				: true, // if false it doesn't generate <img src='folder.gif' ></img > sth like this 
		useStatusText		: true,
		closeSameLevel	:false,
		inOrder					:true 
	};
	//icon['root']='img/base.gif'
	// or icon.root='img/base.gif'
	
	this.icon = {
		root				: 'img/base.gif',
		folder			: 'img/folder.gif',
		folderOpen	: 'img/folderopen.gif',
		node				: 'img/page.gif',
		empty				: 'img/empty.gif',
		line				: 'img/line.gif',
		join				: 'img/join.gif',
		joinBottom	: 'img/joinbottom.gif',
		plus				: 'img/plus.gif',
		plusBottom	: 'img/plusbottom.gif',
		minus				: 'img/minus.gif',
		minusBottom	: 'img/minusbottom.gif',
		nlPlus			: 'img/nolines_plus.gif',
		nlMinus			: 'img/nolines_minus.gif'
	};
	//  name of this tree
	this.obj = objName;
	this.aNodes = [];	// allNodes a arrays 
	this.aIndent = []// aIndent like this [ 0,0,0,0,0,1] , means  if the aIndent[i] is 0 then it will genernate a <img  src='empty.gif'> or aIndent[i]=1				 < img src='line.gif' >  so that the tree will  be generated in html file;
	this.root = new Node(-1);
	this.selectedNode = null;
	// whether selectedNode exists,maybe
	this.selectedFound = false;
	this.completed = false;
};

// Adds a new node to the node array
dTree.prototype.add = function(id, pid, name, url, title, target,   open) {
	if (pid==null){
		pid=-1;
	}
	if(  typeof(pid) =="string"  &&pid=="" ){
		pid=-1;
	}
	this.aNodes[this.aNodes.length] = new Node(id, pid, name, url, title, target, null, null, open);
};

dTree.prototype.add2 = function(id, pid, name, url, title, target, icon, iconOpen, open) {
	if (pid==null){
		pid=-1;
	}
    this.aNodes[this.aNodes.length] = new Node(id, pid, name, url, title, target, icon, iconOpen, open);
};

// Open/close all nodes
dTree.prototype.openAll = function() {
	this.oAll(true);
};
dTree.prototype.closeAll = function() {
	this.oAll(false);
};

// the function is to show the tree in dom  Element 
// so that you can use tagNode.appendChild(), or insertBefore() to insert the tree in the html 
// it just looks like toString () , you can use tagNode.innerHTML=tree.toString()
dTree.prototype.toDOMElement=function(){
	var dtreeTag= document.createElement('div');
	dtreeTag.setAttribute('class',"dtree");
	if(document.getElementById){
		if (this.config.useCookies){
		       	this.selectedNode = this.getSelected();
		}
		this.addNodeTag(this.root,dtreeTag);
			
	}else{
		dtreeTag.appendChild(document.createTextNode('Browser not supported'));

	}

	if (!this.selectedFound) this.selectedNode = null;
	this.completed = true;
	return dtreeTag;
}
// Outputs the tree to the page
dTree.prototype.toString = function() {
	var str = '<div class="dtree">\n';
	if (document.getElementById) {
		if (this.config.useCookies) this.selectedNode = this.getSelected();
		str += this.addNode(this.root);
	} else str += 'Browser not supported.';
	str += '</div>';
	if (!this.selectedFound) this.selectedNode = null;
	this.completed = true;
	return str;
};







	// treeparentNodeOjb is a Node object 
	//  divTagParen is a div tag element 
	//  treeparentNodeOjb'children will be the children of tagparent
	// dTree.prototype.addNode = function(pNode) do the same work but this one return it as DOM Element and addNode return as a String  ,
	// return a divTagNode
	// put the children of treeParentNodeObj under divTagParent
	// <div idvTagParent>
	// 	<div child1 of treeparentNodeObj>
	//	<div child2 of treeparentNodeObj>
	//		...
	// </div>
dTree.prototype.addNodeTag=function (treeParentNodeObj,divTagParent){
	var n=0;
	if (this.config.inOrder) n = treeParentNodeObj._ai;
	for (n; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == treeParentNodeObj.id) {// if treeParentNodeObj has Child
			var cn = this.aNodes[n];//childNode
			cn._p = treeParentNodeObj;
			cn._ai = n;
			// change the Child , Sibling property of  cn
			this.setCS(cn);

			if (!cn.target && this.config.target) cn.target = this.config.target;//target is <a href='' target='parent' >
			if (cn._hc && !cn._io && this.config.useCookies) cn._io = this.isOpen(cn.id);
			if (!this.config.folderLinks && cn._hc) cn.url = null;
			if (this.config.useSelection && cn.id == this.selectedNode && !this.selectedFound) {
					cn._is = true;
					this.selectedNode = n;
					this.selectedFound = true;
			}
			//str += this.node(cn, n);
	//		divTagParent.appendChild(this.nodeTag(cn,n));	
			this.nodeTag(cn,n,divTagParent);
			if (cn._ls) break;// if cn is  lastSbling
		}
	}


	return divTagParent
	}
// Creates the tree structure
// pNode means parentNode
dTree.prototype.addNode = function(pNode) {
	var str = '';
	var n=0;
	if (this.config.inOrder) n = pNode._ai;
	for (n; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == pNode.id) {// if pNode has Child
			var cn = this.aNodes[n];//childNode
			cn._p = pNode;
			cn._ai = n;
			// change the Child , Sibling property of  cn
			this.setCS(cn);

			if (!cn.target && this.config.target) cn.target = this.config.target;//target is <a href='' target='parent' >
			if (cn._hc && !cn._io && this.config.useCookies) cn._io = this.isOpen(cn.id);
			if (!this.config.folderLinks && cn._hc) cn.url = null;
			if (this.config.useSelection && cn.id == this.selectedNode && !this.selectedFound) {
					cn._is = true;
					this.selectedNode = n;
					this.selectedFound = true;
			}
			str += this.node(cn, n);
			if (cn._ls) break;// if cn is  lastSbling
		}
	}
	return str;
};


// Creates the node icon, url and text
// generate the html-text depend on the property of Node object
// return a div tag Element depend on the node return pDiv actually it needn't ,because pDiv is the parameter of nodeTag 
//
// <pDiv>
// 	<div node >
// 	<div class='clip'>// if node has child
//		<div child1 of node >
//		<div child2 of node >
// 	</div>
// </pDiv>
dTree.prototype.nodeTag = function(node, nodeId,pDiv) {
//	var str = '<div class="dTreeNode">' + this.indent(node, nodeId); // use the empty.gif and line.gif to get the structure of a tree
	var dTreeNodeTag= document.createElement('div');
	pDiv.appendChild(dTreeNodeTag);
	dTreeNodeTag.setAttribute('class','dTreeNode');
	this.indentTag(node,nodeId,dTreeNodeTag);
	if (this.config.useIcons) {
		if (!node.icon) node.icon = (this.root.id == node.pid) ? this.icon.root : ((node._hc) ? this.icon.folder : this.icon.node);
		if (!node.iconOpen) node.iconOpen = (node._hc) ? this.icon.folderOpen : this.icon.node;
		if (this.root.id == node.pid) {
			node.icon = this.icon.root;
			node.iconOpen = this.icon.root;
		}
		var imgTag= document.createElement('img');
		imgTag.setAttribute('id',"i"+this.obj+nodeId);
		imgTag.setAttribute('src',( (node._io)?node.iconOpen:node.icon ) );
		imgTag.setAttribute('alt','');
		dTreeNodeTag.appendChild(imgTag);

	}

		var aTag= document.createElement('a');
		dTreeNodeTag.appendChild(aTag);
		aTag.appendChild(document.createTextNode(node.name));
	if (node.url) {// <a id='' class='' href=url title='' target='_parent,and so on'  onmouseover='window.status= ' onclick=' dTree.o(nodeId) if this node is a life node ' >
		aTag.setAttribute('id',"s"+this.obj+nodeId);
		aTag.setAttribute('class',((this.config.useSelection)?(   (node._is?'nodeSel':'node')  ) :'node'  ) );
		aTag.setAttribute('href',node.url);
		if(node.title)
			aTag.setAttribute('title',node.title);
		if(node.target)
			aTag.setAttribute('target',node.target);

		if (this.config.useStatusText){
		      aTag.setAttribute('onmouseover','window.status=\" '+node.name+"\" ;return false;");
		      aTag.setAttribute('onmouseout','window.status='+" \"\";return false;");
		}
			
		if (this.config.useSelection && ((node._hc && this.config.folderLinks) || !node._hc))
			aTag.setAttribute('onclick',"javascript:"+this.obj+'.s('+nodeId+");");
	}else if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id){
	// < a href="javascript:this.obj.o(nodeId)" > means open or close  the children tree 
		aTag.setAttribute('href',"javascript:"+this.obj+'.o('+nodeId+");");
		aTag.setAttribute('class','node');
	}

	/*????????????????????????????????// 此处可能会有bug ，因为此句好像是说如果没有url或者。。时才加</a> 但用DOM 后总是有</a>
	if (node.url || ((!this.config.folderLinks || !node.url) && node._hc))
       		str += '</a>';
		*/

	if (node._hc) {// if the node hasChild
		var clipDivTag= document.createElement('div');
		pDiv.appendChild(clipDivTag);
		clipDivTag.setAttribute('id','d'+this.obj+nodeId);
		clipDivTag.setAttribute('class','clip');
		clipDivTag.setAttribute('style',"display:"+((this.root.id==node.pid||node._io)? 'block':'none')+";" );
		//str += '<div id="d' + this.obj + nodeId + '" class="clip" style="display:' + ((this.root.id == node.pid || node._io) ? 'block' : 'none') + ';">';
		this.addNodeTag(node,clipDivTag);
	}
	this.aIndent.pop();
	return  pDiv;
};
// Creates the node icon, url and text
// generate the html-text depend on the property of Node object
dTree.prototype.node = function(node, nodeId) {
	var str = '<div class="dTreeNode">' + this.indent(node, nodeId); // use the empty.gif and line.gif to get the structure of a tree
	if (this.config.useIcons) {
		if (!node.icon) node.icon = (this.root.id == node.pid) ? this.icon.root : ((node._hc) ? this.icon.folder : this.icon.node);
		if (!node.iconOpen) node.iconOpen = (node._hc) ? this.icon.folderOpen : this.icon.node;
		if (this.root.id == node.pid) {
			node.icon = this.icon.root;
			node.iconOpen = this.icon.root;
		}
		str += '<img id="i' + this.obj + nodeId + '" src="' + ((node._io) ? node.iconOpen : node.icon) + '" alt="" />';
	}

	if (node.url) {// <a id='' class='' href=url title='' target='_parent,and so on'  onmouseover='window.status= ' onclick=' dTree.o(nodeId) if this node is a life node ' >
		str += '<a id="s' + this.obj + nodeId + '" class="' + ((this.config.useSelection) ? ((node._is ? 'nodeSel' : 'node')) : 'node') + '" href="' + node.url + '"';
		if (node.title) str += ' title="' + node.title + '"';
		if (node.target) str += ' target="' + node.target + '"';
		if (this.config.useStatusText) str += ' onmouseover="window.status=\'' + node.name + '\';return true;" onmouseout="window.status=\'\';return true;" ';
		if (this.config.useSelection && ((node._hc && this.config.folderLinks) || !node._hc))
			str += ' onclick="javascript:' + this.obj + '.s(' + nodeId + ');"';
		str += '>';
	}
	// < a href="javascript:this.obj.o(nodeId)" > means open or close  the children tree 
	else if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id)
		str += '<a href="javascript:' + this.obj + '.o(' + nodeId + ');" class="node">';

	str += node.name;
	if (node.url || ((!this.config.folderLinks || !node.url) && node._hc)) str += '</a>';
	str += '</div>';
	if (node._hc) {// if the node hasChild
		str += '<div id="d' + this.obj + nodeId + '" class="clip" style="display:' + ((this.root.id == node.pid || node._io) ? 'block' : 'none') + ';">';
		str += this.addNode(node);
		str += '</div>';
	}
	this.aIndent.pop();
	return str;
};

// Adds the empty (empty.gif)or line(line.gif) icons so that it looks like a tree in html 
//then return the genernated string  of html 
//<div divTag>
//	<img >
//	<img>	
//	<a href >node.url<a>
//</div>
dTree.prototype.indentTag = function(node, nodeId, divTag) {
	if (this.root.id != node.pid) {
		for (var n=0; n<this.aIndent.length; n++){
			var imgTag= document.createElement('img');
			imgTag.setAttribute('src',(this.aIndent[n]==1&&this.config.useLines)?this.icon.line:this.icon.empty);
			imgTag.setAttribute('alt',"");
			divTag.appendChild(imgTag);
		}

			(node._ls) ? this.aIndent.push(0) : this.aIndent.push(1);
		
		if (node._hc) {// <a href="javascript:objName.o(nodeId);<img> </a> "
			//str += '<a href="javascript:' + this.obj + '.o(' + nodeId + ');"><img id="j' + this.obj + nodeId + '" src="';
			var aTag= document.createElement('a');
			aTag.setAttribute("href","javascript:"+this.obj+'.o('+nodeId+");");
			divTag.appendChild(aTag);
			
			var imgTag= document.createElement('img');
			imgTag.setAttribute('id',"j"+this.obj+nodeId);
			imgTag.setAttribute('alt','');
			if (!this.config.useLines){
				imgTag.setAttribute('src' ,(node._io)?this.icon.nlMinux:this.icon.nlPlus);
			}else{ 
				imgTag.setAttribute('src', ( (node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus ) ));
				}
				aTag.appendChild(imgTag);
		}else{
			var imgTag= document.createElement('img');
			imgTag.setAttribute('alt','');
			imgTag.setAttribute('src', ((this.config.useLines)?((node._ls)?this.icon.joinBottom:this.icon.join):this.icon.empty));
			divTag.appendChild(imgTag);
		}

		
	}
	return divTag; 
};
// Adds the empty (empty.gif)or line(line.gif) icons so that it looks like a tree in html 
//then return the genernated string  of html 
dTree.prototype.indent = function(node, nodeId) {
	var str = '';
	if (this.root.id != node.pid) {
		for (var n=0; n<this.aIndent.length; n++)//
			// arrange the empty.gif or line.gif to genernate the structure of a tree
			str += '<img src="' + ( (this.aIndent[n] == 1 && this.config.useLines) ? this.icon.line : this.icon.empty ) + '" alt="" />';

			(node._ls) ? this.aIndent.push(0) : this.aIndent.push(1);
		
		if (node._hc) {// <a href="javascript:objName.o(nodeId);<img> </a> "
			str += '<a href="javascript:' + this.obj + '.o(' + nodeId + ');"><img id="j' + this.obj + nodeId + '" src="';
			if (!this.config.useLines) str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;
			else str += ( (node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus ) );
			str += '" alt="" /></a>';
		} else str += '<img src="' + ( (this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join ) : this.icon.empty) + '" alt="" />';
	}
	return str;
};

// Checks if a node has any children and if it is the last sibling
// C means children s means sibling
dTree.prototype.setCS = function(node) {
	var lastId;
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.id)//   if  some node in the tree is the child of 'node'
			node._hc = true;
		if (this.aNodes[n].pid == node.pid) // if some node in the tree is the brother of 'node'
		       		lastId = this.aNodes[n].id;
	}
	if (lastId==node.id) node._ls = true;// change whether this 'node' is the lastSibling among brothers
};



// Returns the selected node
dTree.prototype.getSelected = function() {
	var sn = this.getCookie('cs' + this.obj);
	return (sn) ? sn : null;
};

// Highlights the selected node
dTree.prototype.s = function(id) {
	if (!this.config.useSelection) return;
	var cn = this.aNodes[id];
	if (cn._hc && !this.config.folderLinks) return;
	if (this.selectedNode != id) {
		if (this.selectedNode || this.selectedNode==0) {
			eOld = document.getElementById("s" + this.obj + this.selectedNode);
			eOld.className = "node";
		}
		eNew = document.getElementById("s" + this.obj + id);
		eNew.className = "nodeSel";
		this.selectedNode = id;
		if (this.config.useCookies) this.setCookie('cs' + this.obj, cn.id);
	}
};

// Toggle Open or close // open close open close  ,so it change the status of open or closed status
// when click on the node <a javascript:='o(nodeId)' > it will open or close the children tree if exists 
dTree.prototype.o = function(id) {
	var cn = this.aNodes[id];//childNode
	this.nodeStatus(!cn._io, id, cn._ls);// change the io status the the  different status ( true ( open) or false (closed))
	cn._io = !cn._io;
	if (this.config.closeSameLevel) this.closeLevel(cn);// close all the node'children which are in the  same level with the id 
	if (this.config.useCookies) this.updateCookie();
};

// Open or close all nodes
// status is true or false if true means to open all nodes 
// 			   if false means to close all nodes 
//this .openAll()=oAll(true); this.closeAll=oAll(false);
dTree.prototype.oAll = function(status) {
	//  aNodes means 'allNodes' its a array of Node object  @jixiuf
	for (var n=0; n<this.aNodes.length; n++) {

		if (this.aNodes[n]._hc && this.aNodes[n].pid != this.root.id) {
			//change the status ( open or closed ) of every node 
			this.nodeStatus(status, n, this.aNodes[n]._ls)
				//   now  declare the status of the node 
			this.aNodes[n]._io = status;
		}
	}
	if (this.config.useCookies) this.updateCookie();
};

// Opens the tree to a specific node
dTree.prototype.openTo = function(nId, bSelect, bFirst) {
	if (!bFirst) {
		for (var n=0; n<this.aNodes.length; n++) {
			if (this.aNodes[n].id == nId) {
				nId=n;
				break;
			}
		}
	}
	var cn=this.aNodes[nId];
	if (cn.pid==this.root.id || !cn._p) return;
	cn._io = true;
	cn._is = bSelect;
	if (this.completed && cn._hc) this.nodeStatus(true, cn._ai, cn._ls);
	if (this.completed && bSelect) this.s(cn._ai);
	else if (bSelect) this._sn=cn._ai;
	this.openTo(cn._p._ai, false, true);
};

// Closes all nodes on the same level as certain node
//this is a private method called by tree.o();to open or close a tree
dTree.prototype.closeLevel = function(node) {
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.pid && this.aNodes[n].id != node.id && this.aNodes[n]._hc) {// if the node in the tree is the brother of 'node' and the node hasChild 

			this.nodeStatus(false, n, this.aNodes[n]._ls);// change the isOpen status to closed ( false ) 
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);
		}
	}
}

// Closes all children of a node
dTree.prototype.closeAllChildren = function(node) {
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.id && this.aNodes[n]._hc) {// if the node in the tree is the child of 'node' and it has child ( if not it needn't close )
			if (this.aNodes[n]._io) this.nodeStatus(false, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);		
		}
	}
}

// Change the status of a node(open or closed)
//  status =true means change it to open  ;
//  	    false means change it to closed
//  	    bottom    means whether this node is the last one ,in order to make sure the icon of the node
dTree.prototype.nodeStatus = function(status, id, bottom) {
	eDiv	= document.getElementById('d' + this.obj + id);
	eJoin	= document.getElementById('j' + this.obj + id);
	if (this.config.useIcons) {
		eIcon	= document.getElementById('i' + this.obj + id);
		eIcon.src = (status) ? this.aNodes[id].iconOpen : this.aNodes[id].icon;
	}
	eJoin.src = (this.config.useLines)? // whether to use line to show the tree in a beautiful feeling
	((status)?  /*make sure the + - depend on the state of open or closed  */(   (bottom)?this.icon.minusBottom:this.icon.minus    ) : (    (bottom)?this.icon.plusBottom : this.icon.plus)        ):
	((status)?this.icon.nlMinus:this.icon.nlPlus);
	eDiv.style.display = (status) ? 'block': 'none';
};


// [Cookie] Clears a cookie
dTree.prototype.clearCookie = function() {
	var now = new Date();
	var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);
	this.setCookie('co'+this.obj, 'cookieValue', yesterday);
	this.setCookie('cs'+this.obj, 'cookieValue', yesterday);
};

// [Cookie] Sets value in a cookie
dTree.prototype.setCookie = function(cookieName, cookieValue, expires, path, domain, secure) {
	document.cookie =
		escape(cookieName) + '=' + escape(cookieValue)
		+ (expires ? '; expires=' + expires.toGMTString() : '')
		+ (path ? '; path=' + path : '')
		+ (domain ? '; domain=' + domain : '')
		+ (secure ? '; secure' : '');
};

// [Cookie] Gets a value from a cookie
dTree.prototype.getCookie = function(cookieName) {
	var cookieValue = '';
	var posName = document.cookie.indexOf(escape(cookieName) + '=');
	if (posName != -1) {
		var posValue = posName + (escape(cookieName) + '=').length;
		var endPos = document.cookie.indexOf(';', posValue);
		if (endPos != -1) cookieValue = unescape(document.cookie.substring(posValue, endPos));
		else cookieValue = unescape(document.cookie.substring(posValue));
	}
	return (cookieValue);
};

// [Cookie] Returns ids of open nodes as a string
dTree.prototype.updateCookie = function() {
	var str = '';
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n]._io && this.aNodes[n].pid != this.root.id) {
			if (str) str += '.';
			str += this.aNodes[n].id;
		}
	}
	this.setCookie('co' + this.obj, str);
};

// [Cookie] Checks if a node id is in a cookie
dTree.prototype.isOpen = function(id) {
	var aOpen = this.getCookie('co' + this.obj).split('.');
	for (var n=0; n<aOpen.length; n++)
		if (aOpen[n] == id) return true;
	return false;
};

// If Push and pop is not implemented by the browser
//  array.push("a"); means to add a item 
if (!Array.prototype.push) {
	Array.prototype.push = function array_push() {
		for(var i=0;i<arguments.length;i++)
			this[this.length]=arguments[i];
		return this.length;
	}
};

if (!Array.prototype.pop) {
	Array.prototype.pop = function array_pop() {
		lastElement = this[this.length-1];
		this.length = Math.max(this.length-1,0);
		return lastElement;
	}
};
