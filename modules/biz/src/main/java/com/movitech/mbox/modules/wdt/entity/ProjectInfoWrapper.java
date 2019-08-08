package com.movitech.mbox.modules.wdt.entity;

import java.util.Date;
import java.util.List;

public class ProjectInfoWrapper {
     private  String ErrorMessage;
     private  int    Result;
     private  List<ProjectInfo> ProjectList;
     private  String ProURL;

    public String getProURL() {
        return ProURL;
    }

    public void setProURL(String proURL) {
        ProURL = proURL;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public List<ProjectInfo> getProjectList() {
        return ProjectList;
    }

    public void setProjectList(List<ProjectInfo> projectList) {
        ProjectList = projectList;
    }
}