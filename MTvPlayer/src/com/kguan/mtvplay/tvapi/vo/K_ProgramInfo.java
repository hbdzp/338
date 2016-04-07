package com.kguan.mtvplay.tvapi.vo;


import com.mstar.android.tvapi.common.vo.ProgramInfo;

public class K_ProgramInfo {
	private ProgramInfo program;
	private static K_ProgramInfo k_ProgramInfo;

	/*public static K_ProgramInfo getInstance(){
		if (k_ProgramInfo == null){
			k_ProgramInfo = new K_ProgramInfo();
		}
		return k_ProgramInfo;
	}*/
	
	public ProgramInfo getProgram() {
		return program;
	}

	public void setProgram(ProgramInfo program) {
		this.program = program;
	}
	
}
