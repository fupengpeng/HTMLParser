package com.example.administrator.htmlparser.model.facytory;

import com.example.administrator.htmlparser.model.impl.ICivilizationFocusingModel;
import com.example.administrator.htmlparser.model.interf.CivilizationFocusingModel;

/**
 * Created by Administrator on 2017/7/26.
 */

public class CivilizationFocusingModelFactory {
    public static ICivilizationFocusingModel newHome(){
        return new CivilizationFocusingModel();
    }
}
