package tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> implements Iterable<TreeNode<T>> {

    public T data;
    public TreeNode<T> parent;
    public List<TreeNode<T>> children;

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    private List<TreeNode<T>> elementsIndex;

    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
        this.elementsIndex = new LinkedList<TreeNode<T>>();
        this.elementsIndex.add(this);
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    public int getLevel() {
        if (this.isRoot())
            return 0;
        else
            return parent.getLevel() + 1;
    }

    private void registerChildForSearch(TreeNode<T> node) {
        elementsIndex.add(node);
        if (parent != null)
            parent.registerChildForSearch(node);
    }

    public TreeNode<T> findTreeNode(Comparable<T> cmp) {
        for (TreeNode<T> element : this.elementsIndex) {
            T elData = element.data;
            if (cmp.compareTo(elData) == 0)
                return element;
        }

        return null;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

    public String getTagName() {
        return ((Tag) data).getName();
    }

    @Override
    public Iterator<TreeNode<T>> iterator() {
        TreeNodeIter<T> iter = new TreeNodeIter<T>(this);
        return iter;
    }

    public TreeNode findNode(TreeNode n, String s) {
        if (n.getTagName().equals(s)) {
            return n;
        } else {
            for (Object child : n.children) {

                TreeNode result = findNode((TreeNode) child, s);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public void printTags(int level) {
        for (int i = 1; i < level; i++) {
            System.out.print("\t");
        }
        System.out.println(((Tag) data).getName());
        for (TreeNode child : children) {
            child.printTags(level + 1);
        }
    }


    public void print(int level) {
        System.out.println();
        for (int i = 1; i < level; i++) {
            System.out.print("\t");
        }

        System.out.print("<" + ((Tag) data).getName() + ">" + ((Tag) data).getValue());
        for (TreeNode child : children) {
            child.print(level + 1);

        }
        if (((Tag) data).getValue().equals("")) {
            System.out.println();
            for (int i = 1; i < level; i++) {
                System.out.print("\t");
            }
        }
        System.out.print("</" + ((Tag) data).getName() + ">");

    }

    public StringBuffer getXML(int level, StringBuffer sb) {
        sb.append("\n");
        for (int i = 1; i < level; i++) {
            sb.append("\t");
        }
        sb.append("<");
        sb.append(((Tag) data).getName());
        sb.append(">");
        sb.append(((Tag) data).getValue());

        for (TreeNode child : children) {
            child.getXML(level + 1,sb);

        }
        if (((Tag) data).getValue().equals("")) {
            sb.append("\n");
            for (int i = 1; i < level; i++) {
                sb.append("\t");
            }
        }
        sb.append("</");
        sb.append(((Tag) data).getName());
        sb.append(">");

        return sb;
    }
}