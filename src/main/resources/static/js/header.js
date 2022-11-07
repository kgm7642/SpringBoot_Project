const nick = document.getElementById("nick");
const body = document.getElementById("body");
const hideMenu = document.querySelector(".hideMenu");


nick.addEventListener("click", () => {
	if(hideMenu.style.opacity==1) {		
		hideMenu.style.opacity = "0";
		return;
	}
	hideMenu.style.opacity = "1.0";
	
});

body.addEventListener("click", () => {
	console.log("sfefesefs");
	hideMenu.style.opacity = "0";
	
});