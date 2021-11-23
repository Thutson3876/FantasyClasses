package me.thutson3876.fantasyclasses.abilities;

import me.thutson3876.fantasyclasses.playermanagement.FantasyPlayer;

public interface Scalable {

	int getScalableValue();
	
	void setScalableValue(int amt);
	
	String getScalableValueName();
	
	static void updateFantasyPlayerValue(FantasyPlayer fplayer, Scalable scal) {
		if(fplayer == null)
			return;
		
		fplayer.setScalableValue(scal.getScalableValueName(), scal.getScalableValue());
	}
}
