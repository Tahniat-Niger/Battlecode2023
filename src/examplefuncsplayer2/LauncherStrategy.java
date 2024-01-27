package examplefuncsplayer2;

import battlecode.common.*;

public class LauncherStrategy {
    /**
     * Run a single turn for a Launcher.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runLauncher(RobotController rc) throws GameActionException {
        // Try to attack someone
        int radius = rc.getType().actionRadiusSquared;
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);
        if (enemies.length > 0) {                   // if enemies more than 0
             MapLocation toAttack = enemies[0].location;
           // MapLocation toAttack = rc.getLocation().add(Direction.EAST);    // not only east

           // moving bot smarter way than attacking, run towards enemy
            // radius -1, go as far as you can, max = -1
            RobotInfo[] VisibleEnemies = rc.senseNearbyRobots(-1, opponent);
            // you can't attack Headquarters
            for(RobotInfo enemy : VisibleEnemies) {
                if(enemy.getType() != RobotType.HEADQUARTERS)
                {
                    // try to move towards it if not HQ
                    // based on our location VS enemy location which way to move
                    // to move towards enemy use direction to
                    MapLocation enemyLocation = enemy.getLocation(); // enemy location
                    MapLocation robotLocation = rc.getLocation();
                    Direction moveDir = robotLocation.directionTo(enemyLocation);
                    if(rc.canMove(moveDir)){
                        rc.move(moveDir);
                    }
                }
            }

            if (rc.canAttack(toAttack)) {
                rc.setIndicatorString("Attacking");
                rc.attack(toAttack);
            }
        }

        // Also try to move randomly.
        Direction dir = RobotPlayer.directions[RobotPlayer.rng.nextInt(RobotPlayer.directions.length)];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }
}
