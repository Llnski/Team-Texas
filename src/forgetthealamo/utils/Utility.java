package forgetthealamo.utils;

import battlecode.common.*;

public class Utility {

    static final Direction[] directions = {
        Direction.NORTH,
        Direction.NORTHEAST,
        Direction.EAST,
        Direction.SOUTHEAST,
        Direction.SOUTH,
        Direction.SOUTHWEST,
        Direction.WEST,
        Direction.NORTHWEST,
    };
    
    public static MapLocation getMapCenter(RobotController rc){
        return new MapLocation(rc.getMapWidth()/2, rc.getMapHeight()/2);
    }
    /**
     * calculates probability of enemy appearing on a given square, takes in robotInfo array to save byteCode.
     * @param rc
     * @param loc
     * @param inView 
     * @return
     */
    public static Double enemyProbability(RobotController rc, MapLocation loc, RobotInfo[] inView){
        Double P = 0.0; //assumption
        return P;
    }

    /**
     * gets direction adjacent to direction minus 30 degrees
     * @param Direction
     * @return
     */
    public static Direction minus30(Direction dir){
        for(int i = 0; i < directions.length; i++){
            if(directions[i] == dir){
                if (i == 0){
                    return directions[directions.length-1];
                }
                return directions[i-1];
            }
        }
        return dir;
    }
    /**
     * gets direction adjacent to direction plus 30 degrees
     * @param Direction
     * @return
     */
    public static Direction plus30(Direction dir){
        for(int i = 0; i < directions.length; i++){
            if(directions[i] == dir){
                if (i == directions.length-1){
                    return directions[0];
                }
                return directions[i+1];
            }
        }
        return dir;
    }
    //000101001010000
    //101001010000
   
    
  public static int[] deserializeRobotLocation(int serial){
        String str = Integer.toBinaryString(serial);
        for (int i = str.length(); i < 16; i++) //this would be one line in python
            str = "0" + str;

        String xS = str.substring(1, 7); String yS = str.substring(7, 13);
        String type = str.substring(12);
        System.out.println(str);
        System.out.println(xS);
        System.out.println(yS);
        int y = 0;
        int x = 0;
        int typeN = 0;
        for (int i = 0; i < 6; i++) {
            if(i < 4 && type.charAt(i) == '1') {
                typeN = typeN + (int)Math.pow(2, 4-i);
            }
            if(xS.charAt(i) == '1') {
                x = x + (int)Math.pow(2, 5-i);
            }
            if(yS.charAt(i) == '1') {
                y = y + (int)Math.pow(2, 5-i);
            } 
        }
        return new int[]{x, y, typeN};
    }
    //0-5 for x(int)
    // 6-11 for y(int)
    //12-14 for Type"
    public static int serializeRobotLocation(RobotInfo ri) {
        String binS = Integer.toBinaryString(ri.getLocation().x);
        for (int i = binS.length(); i < 6; i++) //this would be one line in python
            binS = "0" + binS;

        String yS = Integer.toBinaryString(ri.getLocation().y);
        for (int i = yS.length(); i < 6; i++)
            yS = "0" + yS;

        String vNumber = "111"; //noType for some reason => 111
        switch (ri.getType()) {
            case AMPLIFIER: vNumber = "000";  break;
            case BOOSTER: vNumber = "001";  break;
            case CARRIER: vNumber = "010";  break;
            case HEADQUARTERS: vNumber = "011";  break;
            case DESTABILIZER: vNumber = "100";  break;
            case LAUNCHER: vNumber = "101";  break;
        }

        System.out.println("y: " + yS);
        System.out.println("x: " + binS);
        System.out.println("type: " + vNumber);
        binS = "0" + binS + yS + vNumber;
        int out = 0;
        for (int i = 0; i < 14; i++) {
            if(binS.charAt(i) == '1') {
                out = out + (int)Math.pow(2, 14-i);
            }
        }
        System.out.println(out);
        return out;
    }

     public static int serializeMapLocation(MapLocation m) { //TODO: Figure out how much rubble we can actually have
        String binS = Integer.toBinaryString(m.x);
        for (int i = binS.length(); i < 6; i++) //this would be one line in python
            binS = "0" + binS;

        String yS = Integer.toBinaryString(m.y);
        for (int i = yS.length(); i < 6; i++)
            yS = "0" + yS;

        String vNumber = "0"; //TODO:This is going to break
        
        System.out.println("x: " + binS);
        System.out.println("y: " + yS);
        System.out.println("type: " + vNumber);
        binS = "0" + binS + yS + vNumber;
        int out = 0;
        for (int i = 0; i < 14; i++) {
            if(binS.charAt(i) == '1') {
                out = out + (int)Math.pow(2, 14-i);
            }
        }
        System.out.println(out);
        return out;
    }
 public static int[] deserializeMapLocation(int serial){
        String str = Integer.toBinaryString(serial);
        for (int i = str.length(); i < 16; i++) //this would be one line in python
            str = "0" + str;

        String xS = str.substring(2, 8);
        String yS = str.substring(8, 14);
        String type = str.substring(12);
        System.out.println(str);
        System.out.println("x: " + xS);
        System.out.println("y: " + yS);
        int y = 0;
        int x = 0;
        int typeN = 0;
        for (int i = 0; i < 6; i++) {
            if(i < 4 && type.charAt(i) == '1') {
                typeN = typeN + (int)Math.pow(2, 4-i);
            }
            if(xS.charAt(i) == '1') {
                x = x + (int)Math.pow(2, 5-i);
            }
            if(yS.charAt(i) == '1') {
                y = y + (int)Math.pow(2, 5-i);
            } 
        }
        //TODO: Deserialize type
        return new int[]{x, y, typeN};
    }

    public static RobotType robotTypeIntValue(int i) {
        switch(i){
            case 0: return RobotType.AMPLIFIER;  
            case 1: return RobotType.BOOSTER;  
            case 2: return RobotType.CARRIER;  
            case 3: return RobotType.HEADQUARTERS;  
            case 4: return RobotType.DESTABILIZER;  
            case 5: return RobotType.LAUNCHER;  
        }

        return RobotType.LAUNCHER; 
    }
    public static int getActionRadiusSquared(RobotType targetType, int level) { //TODO: Figure out scaling for level
        // TODO: 
        return targetType.actionRadiusSquared + level;
    }

    public static int[] deserializeCountAndOrder(int readSharedArray) {
        return null;
    }

    public static int[] getSageIndex(RobotController rc) {
        int id = rc.getID();
        for (int i = 1; i <= 3; i++) {

            final int UAP = 15 + (i*15); //unit array position
            int j = UAP + 3;
            int val = -1;
            while (val != id) {//finds sage's group index 
                if(!(j < 15 || (i == 3 && j < 20))){
                    break;
                }
                j++;
                try{
                    val = rc.readSharedArray(j);
                }catch(GameActionException e){
                    val = -1;
                }
            }
            if(val == id){ 
                return new int[]{j, i};
            }

        }
        return new int[]{-1, -1};
    }

}
