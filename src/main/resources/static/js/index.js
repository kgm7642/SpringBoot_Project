header.addEventListener("click", () => {
  // hideMenu 클래스중에 "on"이 포함되어있다면
  if (hideMenu.classList.contains("on")) {
    hideMenu.classList.remove("on");
  } else {
    // 클래스 중에 on 추가
    hideMenu.classList.add("on");
  }
});