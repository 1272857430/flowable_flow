package cn.flow.api.response.form;

import lombok.AllArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
public class Option implements Serializable{
    protected String id;
    protected String name;
}
