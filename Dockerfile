# 베이스 이미지로 OpenJDK 사용 (필요한 버전에 맞게 선택)
FROM openjdk:17-jdk-alpine
ARG JAR_FILE=aio/build/libs/*.jar
# 작업 디렉토리 설정
COPY ${JAR_FILE} app.jar

# 컨테이너 시작 시 .jar 파일을 실행
CMD ["java", "-jar", "aio.jar", "--summarizer.runner-count=5"]