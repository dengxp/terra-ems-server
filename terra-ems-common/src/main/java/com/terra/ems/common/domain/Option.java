package com.terra.ems.common.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 前端选项显示对象
 *
 * @param <T> 值类型
 * @author dengxueping
 */
@Getter
@Setter
@NoArgsConstructor
public class Option<T> implements Serializable {

    private String label;
    private T value;

    public Option(T value, String label) {
        this.label = label;
        this.value = value;
    }

    public static <T> Option<T> of(T value, String label) {
        return new Option<>(value, label);
    }
}
