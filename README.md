# 課題管理アプリ

課題名・詳細・締切日・完了状態を管理するWeb APIです。
ブラウザ画面、またはPostmanから操作できます。

## 使用技術

- Java 17
- Spring Boot 4.0.8
- MyBatis 4.0.1
- PostgreSQL
- HTML / JavaScript

## 起動方法

1. PostgreSQLでデータベースを作成します。

```sql
CREATE DATABASE assignment_db;
```

2. `assignment_db` に接続し、テーブルを作成します。

```sql
CREATE TABLE assignments (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    due_date DATE NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

3. IntelliJの実行構成に環境変数を設定します。

| 環境変数 | 設定値 |
|---|---|
| DATABASE_HOST | localhost |
| DATABASE_PORT | 5432 |
| DATABASE_NAME | assignment_db |
| DATABASE_USER | PostgreSQLのユーザー名 |
| DATABASE_PASSWORD | PostgreSQLのパスワード |

4. Mavenの依存関係を読み込み、Lombokのアノテーション処理を有効にします。
5. `AssignmentApiApplication` を実行します。
6. ブラウザで http://localhost:8080/ を開きます。

## API一覧

| 機能 | メソッド | URL |
|---|---|---|
| 全件取得 | GET | /api/assignments |
| 登録 | POST | /api/assignments |
| 1件取得 | GET | /api/assignments/{id} |
| 削除 | DELETE | /api/assignments/{id} |
| 更新 | PUT | /api/assignments/{id} |

一覧は締切日が近い順に表示します。

## 登録・更新のJSON例

```json
{
  "title": "WebAPIの課題",
  "content": "課題管理アプリを完成させる",
  "dueDate": "2026-11-10",
  "completed": false
}
```

## エラー処理

- 課題名・詳細が空、または締切日が未指定：400 Bad Request
- 指定したIDの課題が存在しない：404 Not Found