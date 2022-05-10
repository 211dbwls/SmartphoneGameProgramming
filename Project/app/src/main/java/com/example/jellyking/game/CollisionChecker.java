package com.example.jellyking.game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.jellyking.framework.CollisionHelper;
import com.example.jellyking.framework.GameObject;

import java.util.ArrayList;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    MainGame game = MainGame.getInstance();

    @Override
    public void update() {
        ArrayList<GameObject> player = game.objectsAt(MainGame.Layer.player);
        ArrayList<GameObject> objects = game.objectsAt(MainGame.Layer.object);

        for(GameObject o1 : player) {
            if(!(o1 instanceof JellyKing)) {  // JellyKing이 아닌 경우 무시.
                continue;
            }
            JellyKing jellyKing = (JellyKing) o1;

            for(GameObject o2 : objects) {
                /* Block */
                if(o2 instanceof Blocks) {  // Block인 경우
                    Blocks block = (Blocks) o2;
                    switch (block.blockType) {
                        case 1:  // Block
                            if(CollisionHelper.collides(block.getBoundingRectFoot(), jellyKing.getBoundingRectHead())) {
                                Log.d(TAG, "Collision : Block(Foot)");
                                jellyKing.jumpUp = false;  // 떨어지도록
                                break;
                            }
                            if(CollisionHelper.collides(block.getBoundingRectLeft(), jellyKing.getBoundingRectRight())) {
                                Log.d(TAG, "Collision : Block(Left)");
                                jellyKing.setMoveDirection(false, true);  // 반대 방향으로 튕기도록
                                break;
                            }
                            if(CollisionHelper.collides(block.getBoundingRectRight(), jellyKing.getBoundingRectLeft())) {
                                Log.d(TAG, "Collision : Block(Right)");
                                jellyKing.setMoveDirection(true, true);  // 반대 방향으로 튕기도록
                                break;
                            }
                            if (CollisionHelper.collides(block.getBoundingRectHead(), jellyKing.getBoundingRectFoot())) {
                                Log.d(TAG, "Collision : Block(Head)");
                                jellyKing.jumpUp = true;   // 점프하도록
                                break;
                            }
                            break;
                        case 2:  // BrokenBlock
                            if(CollisionHelper.collides(block.getBoundingRectFoot(), jellyKing.getBoundingRectHead())) {
                                Log.d(TAG, "Collision : BrokenBlock(Foot)");
                                jellyKing.jumpUp = false;   // 떨어지도록
                                break;
                            }
                            if(CollisionHelper.collides(block.getBoundingRectHead(), jellyKing.getBoundingRectFoot())
                                    || CollisionHelper.collides(block.getBoundingRectHead(), jellyKing.getBoundingRectLeft())
                                    || CollisionHelper.collides(block.getBoundingRectHead(), jellyKing.getBoundingRectRight()) ) {
                                Log.d(TAG, "Collision : BrokenBlock(Head)");
                                jellyKing.jumpUp = true;   // 점프하도록
                                game.remove(block);
                                break;
                            }
                            break;
                        case 3:  // ElectricBlock
                            if (CollisionHelper.collides(block.getBoundingRect(), jellyKing.getBoundingRect())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : ElectricBlock");
                                jellyKing.death();
                                break;
                            }
                            break;
                        case 4:  // JumpBlock
                            if (CollisionHelper.collides(block.getBoundingRectHead(), jellyKing.getBoundingRectFoot())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : JumpBlock");
                                jellyKing.collisionJumpBlock = true;
                                jellyKing.jumpUp = true;   // 점프하도록
                                block.jumpBlockCollision = true;  // 블록 애니메이션
                                break;
                            }
                            break;
                        case 5:  // MoveLRBlock
                            if (CollisionHelper.collides(block.getBoundingRectHead(), jellyKing.getBoundingRectFoot())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : MoveLRBlock");
                                jellyKing.jumpUp = true;   // 점프하도록
                                break;
                            }
                            break;
                        case 6:  // MoveUDBlock
                            if (CollisionHelper.collides(block.getBoundingRectHead(), jellyKing.getBoundingRectFoot())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : MoveUDBlock");
                                jellyKing.jumpUp = true;   // 점프하도록
                                break;
                            }
                            break;
                        case 7:  // StraightLeftBlock
                            if (CollisionHelper.collides(block.getBoundingRectHead(), jellyKing.getBoundingRectFoot())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : StraightLeftBlock");
                                jellyKing.collisionStraightLeftBlock = true;  // 왼쪽으로 이동하도록
                                jellyKing.collisionStraightLeftBlockY = block.startY;
                                break;
                            }
                            break;
                        case 8:  // StraightRightBlock
                            if (CollisionHelper.collides(block.getBoundingRectHead(), jellyKing.getBoundingRectFoot())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : StraightRightBlock");
                                jellyKing.collisionStraightRightBlock = true;  // 오른쪽으로 이동하도록
                                jellyKing.collisionStraightRightBlockY = block.startY;
                                break;
                            }
                            break;
                    }
                }
                /* Enemy */
                else if(o2 instanceof Enemies) {  // Enemy인 경우
                    Enemies enemy = (Enemies) o2;
                    switch (enemy.enemyType) {
                        case 1:  // FixEnemy
                            if (CollisionHelper.collides(enemy.getBoundingRectFoot(), jellyKing.getBoundingRectHead())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : Enemy(FixEnemy Foot)");
                                jellyKing.jumpUp = false;  // 떨어지도록
                                break;
                            }
                            if (CollisionHelper.collides(enemy.getBoundingRectHead(), jellyKing.getBoundingRect())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : Enemy(FixEnemy)");
                                jellyKing.death();
                                break;
                            }
                            break;
                        case 2:  // DropEnemy
                        case 3:  // MoveUDEnemy
                            if (CollisionHelper.collides(enemy.getBoundingRect(), jellyKing.getBoundingRect())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : Enemy(DropEnemy, MoveUDEnemy)");
                                jellyKing.death();
                                break;
                            }
                            break;
                        case 4:  // MoveLREnemy
                            if (CollisionHelper.collides(enemy.getBoundingRectHead(), jellyKing.getBoundingRectFoot())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : MoveLREnemy(Head)");
                                enemy.death();
                                break;
                            }
                            if (CollisionHelper.collides(enemy.getBoundingRect(), jellyKing.getBoundingRect())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : MoveLREnemy");
                                jellyKing.death();
                                break;
                            }
                            break;
                    }
                }
                /* Item */
                else if(o2 instanceof Items) {  // JumpOneItem인 경우
                    Items item = (Items) o2;
                    switch (item.itemType) {
                        case 1:  // JumpOneItem
                            if (CollisionHelper.collides(item.getBoundingRect(), jellyKing.getBoundingRect())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : JumpOneItem");
                                game.remove(item);  // 아이템 삭제
                                jellyKing.setJumpTwice(false);
                                break;
                            }
                            break;
                        case 2:  // JumpInfiniteItem
                            if (CollisionHelper.collides(item.getBoundingRect(), jellyKing.getBoundingRect())) {  // 충돌했을 경우
                                Log.d(TAG, "Collision : JumpInfiniteItem");
                                game.remove(item);  // 아이템 삭제
                                jellyKing.setJumpTwice(true);
                                break;
                            }
                            break;
                    }
                }
                /* Star */
                else if(o2 instanceof Stars) {  // Star인 경우
                    Stars star = (Stars) o2;
                    if (CollisionHelper.collides(star.getBoundingRect(), jellyKing.getBoundingRect())) {  // 충돌했을 경우
                        Log.d(TAG, "Collision : Star");
                        game.remove(star);  // 별 삭제
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
