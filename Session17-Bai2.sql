CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    task_name VARCHAR(255) NOT NULL,
    status VARCHAR(50) DEFAULT 'chưa hoàn thành',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 1. Thêm công việc
CREATE OR REPLACE PROCEDURE add_task(p_task_name VARCHAR, p_status VARCHAR)
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO tasks(task_name, status) VALUES (p_task_name, p_status);
END;
$$;

-- 2. Liệt kê tất cả công việc
CREATE OR REPLACE FUNCTION list_tasks()
RETURNS SETOF tasks
LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY SELECT * FROM tasks ORDER BY id;
END;
$$;

-- 3. Cập nhật trạng thái
CREATE OR REPLACE PROCEDURE update_task_status(p_id INT, p_status VARCHAR)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE tasks SET status = p_status WHERE id = p_id;
END;
$$;

-- 4. Xóa công việc
CREATE OR REPLACE PROCEDURE delete_task(p_id INT)
LANGUAGE plpgsql AS $$
BEGIN
    DELETE FROM tasks WHERE id = p_id;
END;
$$;

-- 5. Tìm kiếm theo tên
CREATE OR REPLACE FUNCTION search_task_by_name(p_keyword VARCHAR)
RETURNS SETOF tasks
LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY 
    SELECT * FROM tasks 
    WHERE task_name ILIKE '%' || p_keyword || '%'
    ORDER BY id;
END;
$$;

-- 6. Thống kê
CREATE OR REPLACE FUNCTION task_statistics()
RETURNS TABLE (
    total_tasks BIGINT,
    completed BIGINT,
    pending BIGINT
)
LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY 
    SELECT 
        COUNT(*)::BIGINT,
        COUNT(CASE WHEN status = 'đã hoàn thành' THEN 1 END)::BIGINT,
        COUNT(CASE WHEN status = 'chưa hoàn thành' THEN 1 END)::BIGINT
    FROM tasks;
END;
$$;