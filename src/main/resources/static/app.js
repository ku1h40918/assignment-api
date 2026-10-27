// app.js
const API_BASE_URL = '/api/assignments';

// エラーメッセージを表示する
function displayError(message) {
    document.getElementById('error-message').textContent =
        'エラー: ' + message;
}

function clearError() {
    document.getElementById('error-message').textContent = '';
}

// APIのエラー本文は文字列として読み取る
async function checkResponse(response) {
    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || `HTTPエラー: ${response.status}`);
    }
}

// フォームから課題データを取得する
function getAssignmentFromForm() {
    return {
        title: document.getElementById('title').value,
        content: document.getElementById('content').value,
        dueDate: document.getElementById('due-date').value,
        completed: document.getElementById('completed').checked
    };
}

// 操作対象のIDを取得する
function getAssignmentId() {
    const input = document.getElementById('assignment-id');
    const id = input.value;

    if (!id || !input.checkValidity()) {
        throw new Error('課題IDには1以上の整数を入力してください。');
    }

    return id;
}

// GET: 全件取得
async function fetchAssignments() {
    clearError();
    const listDiv = document.getElementById('assignments-list');
    listDiv.textContent = 'ロード中...';

    try {
        const response = await fetch(API_BASE_URL);
        await checkResponse(response);
        const assignments = await response.json();

        listDiv.textContent = '';

        if (assignments.length === 0) {
            listDiv.textContent = '課題はありません。';
            return;
        }

        assignments.forEach(assignment => {
            const item = document.createElement('div');
            item.className = 'assignment-item';

            const fields = [
                `ID: ${assignment.id}`,
                `課題名: ${assignment.title}`,
                `詳細: ${assignment.content}`,
                `締切日: ${assignment.dueDate}`,
                `状態: ${assignment.completed ? '完了' : '未完了'}`,
                `作成日時: ${new Date(assignment.createdAt).toLocaleString()}`
            ];

            fields.forEach(text => {
                const line = document.createElement('p');
                line.textContent = text;
                item.appendChild(line);
            });

            listDiv.appendChild(item);
        });
    } catch (error) {
        displayError(`一覧の取得に失敗しました: ${error.message}`);
        listDiv.textContent = '';
    }
}

// POST: 新規登録
document.getElementById('create-form').addEventListener(
    'submit',
    async (event) => {
        event.preventDefault();
        clearError();

        const assignment = getAssignmentFromForm();

        try {
            const response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(assignment)
            });

            await checkResponse(response);

            alert('課題を登録しました！');
            document.getElementById('create-form').reset();
            await fetchAssignments();
        } catch (error) {
            displayError(`登録に失敗しました: ${error.message}`);
        }
    }
);

// GET: 1件取得してフォームに読み込む
async function fetchAssignmentById() {
    clearError();

    try {
        const id = getAssignmentId();
        const response = await fetch(`${API_BASE_URL}/${id}`);
        await checkResponse(response);
        const assignment = await response.json();

        document.getElementById('title').value = assignment.title;
        document.getElementById('content').value = assignment.content;
        document.getElementById('due-date').value = assignment.dueDate;
        document.getElementById('completed').checked = assignment.completed;
    } catch (error) {
        displayError(`検索に失敗しました: ${error.message}`);
    }
}

// PUT: 更新
async function updateAssignment() {
    clearError();

    try {
        const id = getAssignmentId();
        const form = document.getElementById('create-form');

        if (!form.reportValidity()) {
            return;
        }

        const assignment = getAssignmentFromForm();

        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(assignment)
        });

        await checkResponse(response);

        alert('課題を更新しました！');
        await fetchAssignments();
    } catch (error) {
        displayError(`更新に失敗しました: ${error.message}`);
    }
}

// DELETE: 削除
async function deleteAssignment() {
    clearError();

    try {
        const id = getAssignmentId();

        if (!confirm(`本当にID ${id} の課題を削除しますか？`)) {
            return;
        }

        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE'
        });

        await checkResponse(response);

        alert('課題を削除しました。');
        document.getElementById('create-form').reset();
        document.getElementById('assignment-id').value = '';
        await fetchAssignments();
    } catch (error) {
        displayError(`削除に失敗しました: ${error.message}`);
    }
}

// ページロード時に一覧を取得
window.onload = fetchAssignments;