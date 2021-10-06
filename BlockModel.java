package com.example.curdmanagementapp.model;

import android.content.Intent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class BlockModel {

    private  int index,nonce;
    private  long timestamp;
    private  String hash,perviousHash,data;

    public BlockModel(int index, long timestamp, String perviousHash, String data) {
        this.index = index;
        this.timestamp = timestamp;
        this.perviousHash = perviousHash;
        this.data = data;
        nonce = 0;
        hash = BlockModel.calculateHash_details(this); 
    }

    public static String calculateHash_details(BlockModel blockModel) {

        if(blockModel != null){
            MessageDigest messageDigest;
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }

            String txt = blockModel.str();
            final byte[] bytes = messageDigest.digest(txt.getBytes());
            final StringBuilder stringBuilder = new StringBuilder();
            for (final byte b : bytes){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1){
                    stringBuilder.append("0");
                }
                stringBuilder.append(hex);
            }
            return stringBuilder.toString();
        }

        return null;
    }

    public String str() {
        return  index+timestamp+perviousHash+data+nonce;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPerviousHash() {
        return perviousHash;
    }

    public void setPerviousHash(String perviousHash) {
        this.perviousHash = perviousHash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void mineBlock(int diff){
        nonce = 0;
        while (!getHash().substring(0,diff).equals(addZerios(diff))){
            nonce++;
            hash = BlockModel.calculateHash_details(this);
        }
    }

    private String addZerios(int diff) {
        StringBuilder builder = new StringBuilder();
        for (int i =0;i<diff;i++){
            builder.append("0");
        }
        return builder.toString();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Block #").append(index).append(" [previousHash : ").append(perviousHash).append(", ").
                append("timestamp : ").append(new Date(timestamp)).append(", ").append("data : ").append(data).append(", ").
                append("hash : ").append(hash).append("]");
        return builder.toString();
    }
}



