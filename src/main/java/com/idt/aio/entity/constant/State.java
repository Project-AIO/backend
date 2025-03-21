package com.idt.aio.entity.constant;

import lombok.Getter;

@Getter
public enum State {
    //pending, serving, ready는 사용자가 아닌 core server에서 변경
    PENDING, //Processing 중
    SERVING,  // 처리 완료
    INACTIVE, // 사용자가 serving된 것을 inactive로 변경할 수 있음
    READY // 큐 대기
}
