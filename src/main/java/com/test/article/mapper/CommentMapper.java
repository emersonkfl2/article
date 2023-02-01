package com.test.article.mapper;

import com.test.article.dto.CommentDto;
import com.test.article.model.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    private final ModelMapper mapper;

    public CommentMapper(ModelMapper mapper){
        this.mapper = mapper;
    }

    public Comment toEntity(CommentDto commentDto){
        return mapper.map(commentDto, Comment.class);
    }

    public CommentDto toDto(Comment comment){
        return mapper.map(comment, CommentDto.class);
    }

    public List<CommentDto> toCommentDtoList(List<Comment> comments){
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
