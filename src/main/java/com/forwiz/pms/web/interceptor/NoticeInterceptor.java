package com.forwiz.pms.web.interceptor;

import com.forwiz.pms.domain.message.dto.NoticePopupResponse;
import com.forwiz.pms.domain.message.dto.MessageState;
import com.forwiz.pms.domain.message.service.MessageService;
import com.forwiz.pms.domain.user.dto.PmsUserDetails;
import com.forwiz.pms.domain.user.entity.PmsUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeInterceptor implements HandlerInterceptor {
    private final MessageService messageService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -3);
        Date stDate = c.getTime();

        PmsUserDetails principal = (PmsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PmsUser user = principal.getPmsUser();

        List<NoticePopupResponse> messageList = messageService.findNoticePopupInfo(user, stDate);
        request.setAttribute("messageList", messageList);

        int unreadMessageCount = messageService.countByMessageState(MessageState.UNREAD, user);

        request.setAttribute("unreadMsgCount", unreadMessageCount);
    }
}
