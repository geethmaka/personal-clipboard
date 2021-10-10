package com.example.curdmanagementapp;

import android.app.Application;

import com.example.curdmanagementapp.manager.BlockChainManager;
import com.example.curdmanagementapp.model.BlockModel;

public class testChain {
    public static void main(String[] args) {
        BlockChainManager blockChainManager = new BlockChainManager(4);
        blockChainManager.addBlock(blockChainManager.newBlock("logi : 22.22,latiture : 71.222"));
        blockChainManager.addBlock(blockChainManager.newBlock("logi : 22.22,latiture : 71.223"));
        blockChainManager.addBlock(blockChainManager.newBlock("logi : 22.22,latiture : 71.224"));

        System.out.println("Blockchain valid ? " + blockChainManager.isBlocakValid());
        System.out.println(blockChainManager);
    }
}
