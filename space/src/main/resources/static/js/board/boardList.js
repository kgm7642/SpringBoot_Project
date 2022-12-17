
// 스크롤 이벤트 발생시 1 증가 (페이지를 뜻함)
let scrollCnt = 1;

// 스크롤 이벤트 
window.onscroll = function (e) {
	if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
		setTimeout(function () {
    		// 스크롤 최하단 이동시 1증가(페이지 1증가)
			scrollCnt++;
			boardBoxScroll(scrollCnt, 30, selectArry);
		}, 500);
	}
};

// 필터를 몇번 선택했는지 확인 (선택 횟수에 따라 html 태그 배치가 달라짐)
let pageCnt = 0;

// 선택된 필터 요소를 담아둘 리스트 선언
let selectArry = [];

// 스크롤 이벤트에 의한 게시글 리스트를 가져올 함수
function boardBoxScroll(pageNum, amount, selectArry) {
	// pageNum : 가져올 페이지, amount : 한 페이지에 게시글 개수(현재는 30개), selectArry : 필터에서 선택된 요소
	let json = {"pageNum":pageNum, "amount":amount, "selectArry":selectArry};
	let boardItemTag = document.querySelector(".boardItem");
	// 필터에서 선택된 요소가 없다면 'nothing' 값이 배열에 저장됨
	if(selectArry[0] == null) selectArry[0] = "nothing";
	console.log("selectArry 확인 스크롤 이벤트: " + selectArry);
    $.ajax({
        type: "POST",
        url: "/board/getScroll",
		data:JSON.stringify(json),
		contentType: "application/json"
    })
        .done(function (result) {
        	// replaceWith함수를 통해 새로운 데이터를 가져올때마다 태그를 교체해 줄 예정
            $(".boardNull").remove();
            $(".boardItem").append("<div class=boardScroll></div>");
            $(".boardScroll").replaceWith(result);
            
            // 일단은 게시글 리스트 한번 불러왔기 때문에 1 증가
            pageCnt++;
        })
        .fail(function(jqXHR) {
            console.log(jqXHR);
        })
        .always(function() {
            console.log("스크롤 이벤트에 의한 ajax 실행");
        })
}

// 페이지에 처음 접속할 때 게시글 30개를 로딩할 함수
function boardBox(selectArry) {
	console.log("selectArry 확인 페이지 처음 접속: " + selectArry);
	let json = {"selectArry":selectArry};
    $.ajax({
        type: "POST",
        url: "/board/getList",
		data:JSON.stringify(json),
		contentType: "application/json"
    })
        .done(function (result) {
            if(pageCnt==0){
	            $(".section").append("<div class=boardBox></div>");
	            $(".boardBox").replaceWith(result);
	            pageCnt++;
            } else {
            	$(".boardBlock").remove();
	            $(".section").append("<div class=boardBox></div>");
	            $(".boardBox").replaceWith(result);
            }
        })
        .fail(function(jqXHR) {
            console.log(jqXHR);
        })
        .always(function() {
            console.log("boardBox ajax 실행");
        })
}

// 페이지 로딩시 딱 한번 실행 (게시글 리스트 보여줌)
setTimeout(boardBox, 200);

let skillList = document.getElementsByClassName("skillList");
let skill = document.getElementsByClassName("skill");
let skillChoiceContainer = document.getElementById("skillChoiceContainer");
let reset = document.getElementById("reset");

// 필터를 선택한 횟수
let cnt = 0;

// 필터를 모두 체크 해제하기 위해 필요
let check = 0;

// for문을 통해 필터를 모두 확인
for(let i=0; i<skillList.length; i++){
	skillList[i].onclick = function () {
		console.log("selectArry 확인 필터: " + selectArry);
		console.log("필터 cnt 확인 " + cnt);
		// 만약 처음 필터를 선택한 것이라면 (cnt가 0 이라면)
		if(cnt == 0){
			selectArry.shift();
			cnt++;
			// 선택된 요소를 제외한 모든 요소의 choice 클래스를 제거함(그러면 요소의 opacity속성이 0.2가 됨)
			for(let j=0; j<skillList.length; j++){
				if(j!=i) {
					skillList[j].classList.remove("choice");
				}
			}
			selectArry.push(skill[i].innerHTML);	

			// 서버에 데이터 전달
			boardBox(selectArry);
			scrollCnt = 1;
			
		// 만약 선택된 요소에 choice 클래스가 포함되어 있지 않으면
		} else if(!skillList[i].classList.contains("choice")){
			// choice 클래스를 포함해 줌
			skillList[i].classList += " choice";
			selectArry.push(skill[i].innerHTML);
			
			// 서버에 데이터 전달
			boardBox(selectArry);
			scrollCnt = 1;
			cnt++;
		// 만약 선택된 요소에 choice 클래스가 포함되어 있다면 
		} else if(skillList[i].classList.contains("choice")){
			if(cnt == 1) {
				skillList[i].classList.remove("choice");
				for(let i=0; i<skillList.length; i++) {					
					skillList[i].classList += " choice";
				}
				cnt = 0;
				selectArry = [];
				selectArry[0] = "nothing";
				boardBox(selectArry);
				return;
			}
			cnt--;
			// choice 클래스를 제거해 줌
			skillList[i].classList.remove("choice");
//			let removeHidden = document.querySelector("input[value='"+skillList[i].innerHTML+"']");
//			removeHidden.remove();
			
			// 체크 해제한 요소와 같은 값을 가지고 있는 배열의 값을 제거함
			let isEven = function(value){
				return value != skill[i].innerHTML;
			}
		
			selectArry = selectArry.filter(isEven);
			
			// choice 클래스 제거 후 모든 요소를 돌아봄
			for(let j=0; j<skillList.length; j++){
				// 만약 choice 클래스를 가진 요소가 없다면
				if(!skillList[j].classList.contains("choice")){
				console.log("선택된 요소에 choice 클래스가 없음");
					// check 1 증가
					check++;
				// 만약 choice 클래스를 가진 요소가 있다면
				} else {
				console.log("선택된 요소에 choice 클래스가 있음");
					boardBox(selectArry);
					scrollCnt = 1;
					return;
				}
			}
			console.log("check의 수 : "+check);
			// 만약 check가 47이라면 (모든 요소에 choice 클래스가 없었다면)
			if(check>=47){
				// 모든 요소를 돌며 
				for(let j=0; j<skillList.length; j++){
					// choice 클래스를 추가해 줌
					skillList[j].classList += " choice";
				}
				check = 0;
				cnt = 0;
				console.log("카운트 0으로 초기화");
				selectArry = [];
				selectArry[0] = "nothing";
				boardBox(selectArry);
				scrollCnt = 1;
			}			
		}
	};
}

// 필터 초기화 버튼을 누르면 선택된 필터가 모두 초기화 됨
reset.addEventListener('click', function(){
	cnt = 0;
	check = 0;
	selectArry = [];
	selectArry[0] = "nothing";

	for(let i=0; i<skillList.length; i++){
		skillList[i].className += " choice";
	}
	boardBox(selectArry);
	scrollCnt = 1;
});