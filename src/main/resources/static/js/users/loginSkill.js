let skillList = document.getElementsByClassName("skillList");
let skillChoiceContainer = document.getElementById("skillChoiceContainer");
let reset = document.getElementById("reset");
let selectCnt = 0;
let selectArr = [];


for(let i=0; i<skillList.length; i++){
	skillList[i].onclick = function () {
		selectCnt++;
		if(selectCnt>3){
			alert("최대 3개");
			return;
		}
		
		if(selectCnt==1){
			let select_PTag = document.createElement("p");
			let cancel_PTag = document.createElement("p");
			skillList[i].setAttribute("class", "skillList");
			skillList[i].style.opacity="0.2";
			select_PTag.setAttribute("class", "select select_PTag"+i);
			select_PTag.setAttribute("id", "select_PTag"+i);
			cancel_PTag.setAttribute("class", "cancel_PTag");
			cancel_PTag.innerHTML = "X";
			select_PTag.innerHTML = skillList[i].innerHTML;
			skillChoiceContainer.appendChild(select_PTag);
			select_PTag.appendChild(cancel_PTag);
			selectArr[0] = skillList[i].innerHTML;
			
			let select_HiddenTag = document.createElement("input");
			select_HiddenTag.setAttribute("type", "hidden");
			select_HiddenTag.setAttribute("value", skillList[i].innerHTML);
			select_HiddenTag.setAttribute("name", "skillarry");
			skillChoiceContainer.appendChild(select_HiddenTag);	
		} else if(selectCnt==2){
			if(selectArr[0]==skillList[i].innerHTML) {
				selectCnt--;
				alert("같은 기술 선택x");
				return;
			} else {
				let select_PTag = document.createElement("p");
				let cancel_PTag = document.createElement("p");
				skillList[i].setAttribute("class", "skillList");
				skillList[i].style.opacity="0.2";
				select_PTag.setAttribute("class", "select select_PTag"+i);
				select_PTag.setAttribute("id", "select_PTag"+i);
				cancel_PTag.setAttribute("class", "cancel_PTag");
				cancel_PTag.innerHTML = "X";
				select_PTag.innerHTML = skillList[i].innerHTML;
				skillChoiceContainer.appendChild(select_PTag);
				select_PTag.appendChild(cancel_PTag);
				selectArr[1] = skillList[i].innerHTML;
				
				let select_HiddenTag = document.createElement("input");
				select_HiddenTag.setAttribute("type", "hidden");
				select_HiddenTag.setAttribute("value", skillList[i].innerHTML);
				select_HiddenTag.setAttribute("name", "skillarry");
				skillChoiceContainer.appendChild(select_HiddenTag);
			}
		} else {
			if((selectArr[0]==skillList[i].innerHTML)||(selectArr[1]==skillList[i].innerHTML)) {
				selectCnt--;
				alert("같은 기술 선택x");
				return;
			} else {
				let select_PTag = document.createElement("p");
				let cancel_PTag = document.createElement("p");
				skillList[i].setAttribute("class", "skillList");
				skillList[i].style.opacity="0.2";
				select_PTag.setAttribute("class", "select select_PTag"+i);
				select_PTag.setAttribute("id", "select_PTag"+i);
				cancel_PTag.setAttribute("class", "cancel_PTag");
				cancel_PTag.innerHTML = "X";
				select_PTag.innerHTML = skillList[i].innerHTML;
				skillChoiceContainer.appendChild(select_PTag);
				select_PTag.appendChild(cancel_PTag);
				selectArr[2] = skillList[i].innerHTML;
				
				let select_HiddenTag = document.createElement("input");
				select_HiddenTag.setAttribute("type", "hidden");
				select_HiddenTag.setAttribute("value", skillList[i].innerHTML);
				select_HiddenTag.setAttribute("name", "skillarry");
				skillChoiceContainer.appendChild(select_HiddenTag);
			}
		}
	};
}
reset.addEventListener('click', function(){
	while(skillChoiceContainer.hasChildNodes()) {			
		skillChoiceContainer.removeChild(skillChoiceContainer.firstChild);
	}
	selectCnt=0;
	for(let i=0; i<skillList.length; i++){
		skillList[i].style.opacity="1";
	}
});