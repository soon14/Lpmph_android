package com.ailk.treeview;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * http://blog.csdn.net/lmj623565791/article/details/40212367
 * @author zhy
 *
 */
public class TreeHelper
{
	/**
	 * 传入我们的普通bean，转化为我们排序后的Node
	 * 
	 * @param datas
	 * @param defaultExpandLevel
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException

	{
		List<Node> result = new ArrayList<>();
		// 将用户数据转化为List<Node>
		List<Node> nodes = convetData2Node(datas);
		// 拿到根节点
		List<Node> rootNodes = getRootNodes(nodes);
		// 排序以及设置Node间关系
		for (Node node : rootNodes)
		{
			addNode(result, node, defaultExpandLevel, 1);
		}
		return result;
	}

	/**
	 * 过滤出所有可见的Node
	 * 
	 * @param nodes
	 * @return
	 */
	public static List<Node> filterVisibleNode(List<Node> nodes)
	{
		List<Node> result = new ArrayList<>();

		for (Node node : nodes)
		{
			// 如果为跟节点，或者上层目录为展开状态
			if (node.isRoot() || node.isParentExpand())
			{
				result.add(node);
			}
		}
		return result;
	}

	/**
	 * 将我们的数据转化为树的节点
	 * 
	 * @param datas
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private static <T> List<Node> convetData2Node(List<T> datas)
			throws IllegalArgumentException, IllegalAccessException

	{
		List<Node> nodes = new ArrayList<>();
		Node node = null;

		for (T t : datas)
		{
			String id = "-1";
			String pId = "-1";
			String label = null;
			Class<? extends Object> clazz = t.getClass();
			Field[] declaredFields = clazz.getDeclaredFields();
			for (Field f : declaredFields)
			{
				if (f.getAnnotation(TreeNodeId.class) != null)
				{
					f.setAccessible(true);
					id = (String) f.get(t);
				}
				if (f.getAnnotation(TreeNodePid.class) != null)
				{
					f.setAccessible(true);
					pId = (String) f.get(t);
				}
				if (f.getAnnotation(TreeNodeLabel.class) != null)
				{
					f.setAccessible(true);
					label = (String) f.get(t);
				}
				if (!id.equals("-1") && !pId.equals("-1") && label != null)
				{
					break;
				}
			}
			node = new Node(String.valueOf(id), String.valueOf(pId), label);
			nodes.add(node);
		}

		/**
		 * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
		 */
		for (int i = 0; i < nodes.size(); i++)
		{
			Node n = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); j++)
			{
				Node m = nodes.get(j);
				if (m.getpId().equals(n.getId()))
				{
					n.getChildren().add(m);
					m.setParent(n);
				} else if (m.getId().equals(n.getpId()))
				{
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}

		return nodes;
	}

	private static List<Node> getRootNodes(List<Node> nodes)
	{
		List<Node> root = new ArrayList<>();
		for (Node node : nodes)
		{
			if (node.isRoot())
				root.add(node);
		}
		return root;
	}

	/**
	 * 把一个节点上的所有的内容都挂上去
	 */
	private static void addNode(List<Node> nodes, Node node,
								int defaultExpandLeval, int currentLevel)
	{

		nodes.add(node);
		if (defaultExpandLeval >= currentLevel)
		{
			node.setExpand(true);
		}

		if (node.isLeaf())
			return;
		for (int i = 0; i < node.getChildren().size(); i++)
		{
			addNode(nodes, node.getChildren().get(i), defaultExpandLeval,
					currentLevel + 1);
		}
	}


}
