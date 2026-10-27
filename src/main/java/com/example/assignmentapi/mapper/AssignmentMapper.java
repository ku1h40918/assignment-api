package com.example.assignmentapi.mapper;

import com.example.assignmentapi.dto.Assignment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentMapper {

    @Select("""
        SELECT id, title, content, due_date, completed, created_at
        FROM assignments
        ORDER BY due_date ASC, id ASC
        """)
    List<Assignment> findAll();

    @Select("""
        SELECT id, title, content, due_date, completed, created_at
        FROM assignments
        WHERE id = #{id}
        """)
    Assignment findById(@Param("id") long id);

    @Insert("""
        INSERT INTO assignments (title, content, due_date, completed)
        VALUES (#{title}, #{content}, #{dueDate}, #{completed})
        """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Assignment assignment);

    @Update("""
        UPDATE assignments
        SET title = #{title},
            content = #{content},
            due_date = #{dueDate},
            completed = #{completed}
        WHERE id = #{id}
        """)
    int update(Assignment assignment);

    @Delete("DELETE FROM assignments WHERE id = #{id}")
    int delete(@Param("id") long id);
}