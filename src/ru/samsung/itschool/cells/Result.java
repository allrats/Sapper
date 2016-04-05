package ru.samsung.itschool.cells;

public class Result {
	public String name;
	public Boolean win;
	public Boolean withFlags;
	public int N;
	public Result(String player, Boolean win, int N, Boolean withFlags)
	{
		name = player;
		this.win = win;
		this.N = N;
		this.withFlags = withFlags;
	}
}
