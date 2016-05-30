package com.ittekikun.plugin.minetweet.data;

import org.bukkit.permissions.Permissible;

public enum Permission
{
	/* 権限ノード */
	TWEET ("Tweet"),
	HELP ("Help"),

	// Admin Commands
	RELOAD ("Reload"),
	;

	// ノードヘッダー
	final String HEADER = "minetweet.";
	private String node;

	/**
	 * コンストラクタ
	 * @param node 権限ノード
	 */
	Permission(final String node)
	{
		this.node = HEADER + node;
	}

	/**
	 * 指定したプレイヤーが権限を持っているか
	 * @param player Permissible. Player, CommandSender etc
	 * @return boolean
	 */
	public boolean has(final Permissible perm)
	{
		if (perm == null) return false;
		return perm.hasPermission(node); // only support SuperPerms
	}
}