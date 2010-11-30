$(
function (){
 var parentTabs= $(".parentTab");
 parentTabs.click( function(){
 	var th=$(this);
 	th.parent().siblings(".tabItem").children(".children").slideUp( 100,null);
 	var children=th.siblings(".children");
 	children.toggle(100,null);
 }
 );
}
)
 