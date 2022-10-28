package com.forwiz.pms.domain.code;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UiController {

    private final UiService uiService;

    @PostMapping("/ui")
    public ResponseEntity<String> create1(@RequestBody UiRequestDto requestDto){

        UiResponseDto dto = uiService.create(TransMapper.toServiceDto(requestDto));

        return ResponseEntity.ok("ok");
    }

    @PostMapping("/ui2")
    public ResponseEntity<String> create2(@RequestBody UiRequestDto requestDto){

        UiResponseDto dto = uiService.create(requestDto.toServiceDto());

        return ResponseEntity.ok("ok");
    }
}
