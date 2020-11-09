package com.alan.cloud.codegenerator.test;

import javax.swing.tree.TreeNode;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * @author 王合
 * @date 2020-04-05 17:32:51
 */
public class Tree {

    public static class TreeNode {
        int data;

        TreeNode leftChild;
        TreeNode rightChild;

        TreeNode(int data) {
            this.data = data;
        }

        public static TreeNode createBinaryTree(LinkedList<Integer> inputList) {
            TreeNode node = null;
            if (inputList == null || inputList.isEmpty()) {
                return null;

            }
            Integer data = inputList.removeFirst();
            if (data != null) {
                node = new TreeNode(data);
                node.leftChild = createBinaryTree(inputList);
                node.rightChild = createBinaryTree(inputList);
            }
            return node;
        }

        public static void preOrderTraveral(TreeNode node) {
            if (node == null) {
                return;
            }
            System.out.println(node.data);
            preOrderTraveral(node.leftChild);
            preOrderTraveral(node.rightChild);
        }

        public static void inOrderTraveral(TreeNode node) {
            if (node == null) {
                return;
            }
            inOrderTraveral(node.leftChild);
            System.out.println(node.data);
            inOrderTraveral(node.rightChild);
        }

        public static void postOrderTraveral(TreeNode node) {
            if (node == null) {
                return;
            }
            postOrderTraveral(node.leftChild);
            postOrderTraveral(node.rightChild);
            System.out.println(node.data);
        }


        public static void main(String args[]) {
            LinkedList<Integer> inputList = new LinkedList<>(Arrays.asList(new Integer[]{3, 2, 9, null, null, 10, null, 8, null, 4}));
            TreeNode node = createBinaryTree(inputList);
            preOrderTraveral(node);

        }
    }
}
