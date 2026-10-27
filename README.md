# 課題管理アプリ

課題名・詳細・締切日・完了状態を管理するWeb APIです。
ブラウザ画面、またはPostmanから操作できます。

## 使用技術

- Java 17
- Spring Boot 4.0.8
- MyBatis 4.0.1
- PostgreSQL
- HTML / JavaScript

## API一覧

| 機能 | メソッド | URL |
|---|---|---|
| 全件取得 | GET | /api/assignments |
| 登録 | POST | /api/assignments |
| 1件取得 | GET | /api/assignments/{id} |
| 削除 | DELETE | /api/assignments/{id} |
| 更新 | PUT | /api/assignments/{id} |

一覧は締切日が近い順に表示します。

## エラー処理

- 課題名・詳細が空、または締切日が未指定：400 Bad Request
- 指定したIDの課題が存在しない：404 Not Found
