# [Scaling and Parallel Processing]

단일 쓰레드와 단일 프로세스의 작업으로 많은 일괄 처리 작업을 해결할 수 있기 때문에 복잡하게 구현하기전에 요구사항을 올바르게 확인하는 것은 중요합니다.
현실적인 작업의 성능을 측정하고 가장 간단한 구현이 먼저 요구사항을 충족하는지 확인하는 것이 좋습니다.
표준 하드웨어에서 1분 안에 수백 메가의 파일을 쓰고 읽을 수 있습니다.

병럴 처리로 작업을 구현할 준비가 되면 이번 장에서 스프링 배치는 다양한 옵션을 제공할 것 입니다. 하지만 일부 기능은 다른곳에서 다루고 있습니다.
고 수준에서 병렬 처리에는 두가지 모드가 있습니다

- Single Process, multi-threaded
- Multi-process

이들은 다음과 같은 카테고리로 분류됩니다.

- Multi-threaded Step (single process)
- Parallel Steps (single process)
- Remote Chunking of Step (multi process)
- Partitioning a Step (single or multi process)

먼저 single-process 옵션들을 설명하고 그 다음 멀티 프로세스 옵션을 설명합니다.

## Multi-threaded Step

병렬 처리를 시작하는 가장 간단한 방법은 Step configuration 에 `TaskExecutor`를 추가하는 것 입니다.
java configuration 을 사용할 때 `TaskExecutor` 은 다음의 예제처럼 스탭에 추가될 수 있습니다.

```java
@Bean
public TaskExecutor taskExecutor() {
  return new SimpleAsyncTaskExecutor("spring_batch");
}

@Bean
public Step sampleStep(TaskExecutor taskExecutor) {
  return this.stepBuilderFActory.get("sampleStep")
             .<String, String>chunk(10)
             .reader(itemReader())
             .writer(itemWriter())
             .taskExecutor(taskExecutor)
             .build();
}
```

이번 예제에서 `taskExecutor` 는 `TaskExecutor` 를 구현하는 또 다른 빈 정의에 대한 참조입니다.
`TaskExecutor` 는 표준 Spring interface 이며 사용가능한 구현 방법은 Spring User Guide 를 참조할 수 있습니다.

위와 같은 설정으로 `Step` 은 분할되어 실행되는 쓰레드에서 각각의 아이템 묶음(각각 주기적으로 커밋되는)으로 읽고 처리되고 쓰여질 수 있습니다.
