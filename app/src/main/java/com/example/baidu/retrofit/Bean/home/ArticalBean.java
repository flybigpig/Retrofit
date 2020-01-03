package com.example.baidu.retrofit.Bean.home;

import java.util.List;

/**
 * @author
 * @date 2020/1/3.
 * GitHub：
 * email：
 * description：
 */
public class ArticalBean {

    private List<String> category;
    private boolean error;
    private ResultsBean results;


    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getCategory() {
        return category;
    }


    public void setError(boolean error) {
        this.error = error;
    }

    public boolean getError() {
        return error;
    }


    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public ResultsBean getResults() {
        return results;
    }

}
