/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import org.linguate.arborate.vm.BaseType;

/**
 *
 * @author Phil Hutchinson
 */
public class ArborateType {
    protected String name;
    protected BaseType vmType;

    public ArborateType() {
    }
    
    public ArborateType(String name, BaseType vmType) {
        this.name = name;
        this.vmType = vmType;
    }

    public String getName() {
        return name;
    }

    public BaseType getVmType() {
        return vmType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVmType(BaseType vmType) {
        this.vmType = vmType;
    }
}
