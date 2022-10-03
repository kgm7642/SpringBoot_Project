package com.project.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.project.domain.Board;
import com.project.domain.BoardSaveForm;
import com.project.domain.BoardView;
import com.project.domain.Criteria;
import com.project.domain.Page;
import com.project.domain.Smarteditor;
import com.project.service.BoardService;
import com.project.service.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final UsersService usersService;
	
	
	@GetMapping("/list")
	public String boardList(Model model, Criteria cri) {
		model.addAttribute("boardList", boardService.boardList(cri));		
		model.addAttribute("pageMaker", new Page(boardService.getTotal(cri), cri));
		model.addAttribute("skillList", usersService.skill());
		return "/board/boardList";
	}
	
	@ResponseBody
	@PostMapping(value = "/getList", consumes = "application/json")
	public String getList(Model model, Criteria cri) {
		log.info("cri={}",cri);
		model.addAttribute("boardList", boardService.boardList(cri));		
		model.addAttribute("pageMaker", new Page(boardService.getTotal(cri), cri));
		model.addAttribute("skillList", usersService.skill());
		return "/board/boardList";
	}
	
//	게시글 상세보기
	@GetMapping("/view")
	public String boardView(Model model, int boardnumber) {
		log.info("게시글 확인"+boardService.getBoard(boardnumber));
		model.addAttribute("board",boardService.getBoard(boardnumber));
		return "/board/boardView";
	}
	
//	게시글 작성하기 페이지 이동
	@GetMapping("/write")
	public String write(@ModelAttribute Board board, Model model) {
		model.addAttribute("skillList", usersService.skill());
		return "/board/boardWrite";
	}
	
//	게시글 작성 완료
	@PostMapping("/write")
	public String writeFinish(BoardSaveForm form, Principal principal, Model model) {
		form.setBoardwriter(principal.getName());
		log.info("BoardSaveForm을 확인해보자"+form);
		
		boardService.writeBoard(form);
		return "redirect:/";
	}
	
//	@PostMapping("/singleImage")
//	public String singleImage(HttpServletRequest req, Smarteditor smarteditor) throws UnsupportedEncodingException{
//		String callback = smarteditor.getCallback();
//		String callback_func = smarteditor.getCallback_func();
//		String file_result = "";
//		String result = "";
//		MultipartFile multiFile = smarteditor.getFiledata();
//		try{
//			if(multiFile != null && multiFile.getSize() > 0 && 
//	        		StringUtils.isNotBlank(multiFile.getName())){
//				if(multiFile.getContentType().toLowerCase().startsWith("image/")){
//	            	String oriName = multiFile.getName();
//	                String uploadPath = req.getServletContext().getRealPath("/img");
//	                String path = uploadPath + "/smarteditor/";
//	                File file = new File(path);
//	                if(!file.exists()){
//	                file.mkdirs();
//	                }
//	                String fileName = UUID.randomUUID().toString();
//	                smarteditor.getFiledata().transferTo(new File(path + fileName));
//	                file_result += "&bNewLine=true&sFileName=" + oriName + 
//	                			   "&sFileURL=/img/smarteditor/" + fileName;
//				}else{
//					file_result += "&errstr=error";
//				}
//			}else{
//				file_result += "&errstr=error";
//			}
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//		result = "redirect:" + callback + 
//				 "?callback_func=" + URLEncoder.encode(callback_func,"UTF-8") + file_result;
//		return result;
//	}
//	
//	@PostMapping("/multiImage")
//	public String multiImage(HttpServletRequest request, HttpServletResponse response, Smarteditor smarteditor) throws UnsupportedEncodingException {
//	     try {
//             //파일정보
//             String sFileInfo = "";
//             //파일명을 받는다 - 일반 원본파일명
//             String filename = request.getHeader("file-name");
//             //파일 확장자
//             String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
//             //확장자를소문자로 변경
//             filename_ext = filename_ext.toLowerCase();
//             //파일 기본경로
//             String dftFilePath = request.getSession().getServletContext().getRealPath("/");
//             //파일 기본경로 _ 상세경로
//             String filePath = dftFilePath + "resource" + File.separator + "photo_upload" + File.separator;
//             File file = new File(filePath);
//             if(!file.exists()) {
//                file.mkdirs();
//             }
//             String realFileNm = "";
//             SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//             String today= formatter.format(new java.util.Date());
//             realFileNm = today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
//             String rlFileNm = filePath + realFileNm;
//             ///////////////// 서버에 파일쓰기 ///////////////// 
//             InputStream is = request.getInputStream();
//             OutputStream os = new FileOutputStream(rlFileNm);
//             int numRead;
//             byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
//             while((numRead = is.read(b,0,b.length)) != -1){
//                os.write(b,0,numRead);
//             }
//             if(is != null) {
//                is.close();
//             }
//             os.flush();
//             os.close();
//             ///////////////// 서버에 파일쓰기 /////////////////
//             // 정보 출력
//             sFileInfo += "&bNewLine=true";
//             // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
//             sFileInfo += "&sFileName="+ filename;;
//             sFileInfo += "&sFileURL="+"/resource/photo_upload/"+realFileNm;
//             PrintWriter print = response.getWriter();
//             print.print(sFileInfo);
//             print.flush();
//             print.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//		return null;
//	}
}
