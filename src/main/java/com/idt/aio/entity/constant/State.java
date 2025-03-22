package com.idt.aio.entity.constant;

import lombok.Getter;

@Getter
public enum State {
    //inactive랑 serving일 때 api 콜하게 함
    //post 요청 보냄, docId를 보내면 response에 변경된 상태 띄워도 됨 200 콜해도 변경됐다고 인지


    //pending, serving, ready는 사용자가 아닌 core server에서 변경
    PENDING, //Processing 중
    SERVING,  // 처리 완료
    INACTIVE, // 사용자가 serving된 것을 inactive로 변경할 수 있음
    READY // 큐 대기
}
