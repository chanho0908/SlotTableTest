# SlotTable Inspector Demo

Jetpack Compose의 SlotTable 내부 구조를 로그로 출력하여 분석하는 데모 앱입니다.

## 목적

Jetpack Compose의 내부 메커니즘인 **SlotTable**의 `groups` IntArray가 실제로 어떤 데이터를 담고 있는지 눈으로 확인하기 위한 학습용 프로젝트입니다.

## 주요 기능

- SlotTable의 내부 구조를 리플렉션으로 접근
- `groups` IntArray의 각 그룹(5개 필드)을 로그로 출력
- 간단한 UI로 SlotTable 구조 분석 트리거

## SlotTable 구조

SlotTable은 Compose 런타임의 핵심 데이터 구조로, Composition의 상태를 저장합니다.

### Groups IntArray 구조

각 그룹은 5개의 Int 값으로 구성됩니다:

| Index | 필드명 | 설명 |
|-------|--------|------|
| 0 | Key | 그룹을 식별하는 키 값 |
| 1 | Group info | 플래그, 노드 개수 등의 메타데이터 |
| 2 | Parent anchor | 부모 그룹의 앵커 |
| 3 | Size | 그룹의 크기 |
| 4 | Data anchor | 데이터 슬롯의 앵커 |

따라서 `groups.size / 5 = 총 그룹 수`입니다.

## 사용 방법

### 1. 앱 빌드 및 실행

```bash
# 디버그 빌드
./gradlew assembleDebug

# 디바이스에 설치
./gradlew installDebug

# 또는 Android Studio에서 직접 실행
```

### 2. SlotTable 로그 확인

앱 실행 후 **"SlotTable 로그 출력"** 버튼을 클릭하면 Logcat에 로그가 출력됩니다.

```bash
# Logcat 필터링
adb logcat -s SlotTableInspector
```

### 3. 예상 로그 출력

```
D/SlotTableInspector: ========================================
D/SlotTableInspector: SlotTable Structure
D/SlotTableInspector: ========================================
D/SlotTableInspector: Total groups: 15
D/SlotTableInspector: Groups array size: 75
D/SlotTableInspector: ========================================
D/SlotTableInspector:
D/SlotTableInspector: --- 그룹 0 ---
D/SlotTableInspector: Key          : 100
D/SlotTableInspector: Group info   : 268435456
D/SlotTableInspector: Parent anchor: -1
D/SlotTableInspector: Size         : 15
D/SlotTableInspector: Data anchor  : 0
D/SlotTableInspector:
D/SlotTableInspector: --- 그룹 1 ---
D/SlotTableInspector: Key          : 200
D/SlotTableInspector: Group info   : 134217728
D/SlotTableInspector: Parent anchor: 0
D/SlotTableInspector: Size         : 5
D/SlotTableInspector: Data anchor  : 1
...
```

## 프로젝트 구조

```
app/src/main/java/com/peto/slottable/
├── MainActivity.kt           # UI 구현 (Column, Text, Button)
├── SlotTableInspector.kt     # SlotTable 리플렉션 유틸리티
└── ui/theme/                 # Compose 테마 설정
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

## 기술 스택

- **Language**: Kotlin 2.2.10
- **UI**: Jetpack Compose (BOM 2026.02.01)
- **Material Design**: Material3
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 36 (Android 15)

## 주요 구현 내용

### SlotTableInspector.kt

리플렉션을 사용하여 Compose의 internal API에 접근합니다:

```kotlin
// Composition → SlotTable → groups IntArray
val slotTableField = compositionClass.getDeclaredField("slotTable")
val groupsField = slotTableClass.getDeclaredField("groups")
val groups = groupsField.get(slotTable) as IntArray
```

### MainActivity.kt

`currentComposer.composition`을 통해 현재 Composition에 접근:

```kotlin
val composer = currentComposer
val composition = composer.composition

Button(onClick = {
    SlotTableInspector.logSlotTable(composition)
}) {
    Text("SlotTable 로그 출력")
}
```

## 학습 포인트

1. **그룹 수 확인**: `groups.size / 5`가 UI의 Composable 수와 대략 일치하는지 확인
2. **계층 구조**: Parent anchor를 통해 Composable의 계층 관계 파악
3. **Key 값**: Composable의 식별자로 사용되는 키 값 확인
4. **Group info 분석**: 비트 플래그로 인코딩된 메타데이터 이해

## ⚠️ 주의사항

- **DEBUG ONLY**: 이 코드는 절대로 프로덕션 환경에서 사용하면 안 됩니다
- **Internal API**: Compose의 internal API를 사용하므로 향후 버전에서 변경될 수 있습니다
- **리플렉션 오버헤드**: 리플렉션은 성능에 영향을 줄 수 있습니다
- **학습 목적**: 이 프로젝트는 Compose 내부 동작을 이해하기 위한 학습 도구입니다

## 참고 자료

- [Jetpack Compose 공식 문서](https://developer.android.com/jetpack/compose)
- [Compose 컴파일러 및 런타임](https://developer.android.com/jetpack/compose/mental-model)
- [SlotTable 내부 구조 이해](https://dev.to/zachklipp/introduction-to-the-compose-snapshot-system-19cn)

## 라이선스

이 프로젝트는 학습 목적으로 제작되었습니다.

## 기여

SlotTable 분석 기능 개선이나 추가 디버깅 도구에 대한 제안은 언제나 환영합니다!
