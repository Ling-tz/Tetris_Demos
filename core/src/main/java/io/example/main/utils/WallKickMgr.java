package io.example.main.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class WallKickMgr {

    // --- PERBAIKAN: Jadikan PUBLIC STATIC agar bisa diakses TetrisGame ---
    public static class Offset {
        public int x, y;
        public Offset(int x, int y) { this.x = x; this.y = y; }
    }
    // ------------------------------------------------------------------

    private HashMap<String, ArrayList<Offset>> kicksJLSTZ;
    private HashMap<String, ArrayList<Offset>> kicksI;

    public WallKickMgr() {
        kicksJLSTZ = new HashMap<>();
        kicksI = new HashMap<>();
        initData();
    }

    private ArrayList<Offset> createOffsets(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        ArrayList<Offset> list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(x1, y1));
        list.add(new Offset(x2, y2));
        list.add(new Offset(x3, y3));
        list.add(new Offset(x4, y4));
        return list;
    }

    private void initData() {
        // Data SRS J, L, S, T, Z
        kicksJLSTZ.put("0->1", createOffsets(-1, 0, -1, 1, 0, -2, -1, -2));
        kicksJLSTZ.put("1->0", createOffsets(1, 0, 1, -1, 0, 2, 1, 2));
        kicksJLSTZ.put("1->2", createOffsets(1, 0, 1, -1, 0, 2, 1, 2));
        kicksJLSTZ.put("2->1", createOffsets(-1, 0, -1, 1, 0, -2, -1, -2));
        kicksJLSTZ.put("2->3", createOffsets(1, 0, 1, 1, 0, -2, 1, -2));
        kicksJLSTZ.put("3->2", createOffsets(-1, 0, -1, -1, 0, 2, -1, 2));
        kicksJLSTZ.put("3->0", createOffsets(-1, 0, -1, -1, 0, 2, -1, 2));
        kicksJLSTZ.put("0->3", createOffsets(1, 0, 1, 1, 0, -2, 1, -2));

        // Data SRS I (Garis)
        kicksI.put("0->1", createOffsets(-2, 0, 1, 0, -2, -1, 1, 2));
        kicksI.put("1->0", createOffsets(2, 0, -1, 0, 2, 1, -1, -2));
        kicksI.put("1->2", createOffsets(-1, 0, 2, 0, -1, 2, 2, -1));
        kicksI.put("2->1", createOffsets(1, 0, -2, 0, 1, -2, -2, 1));
        kicksI.put("2->3", createOffsets(2, 0, -1, 0, 2, 1, -1, -2));
        kicksI.put("3->2", createOffsets(-2, 0, 1, 0, -2, -1, 1, 2));
        kicksI.put("3->0", createOffsets(1, 0, -2, 0, 1, -2, -2, 1));
        kicksI.put("0->3", createOffsets(-1, 0, 2, 0, -1, 2, 2, -1));
    }

    public ArrayList<Offset> getKicks(String pieceType, int currentState, int nextState) {
        String key = currentState + "->" + nextState;

        if (pieceType.equals("I_Mino")) {
            return kicksI.get(key);
        } else if (pieceType.equals("O_Mino")) {
            ArrayList<Offset> empty = new ArrayList<>();
            empty.add(new Offset(0,0));
            return empty;
        } else {
            return kicksJLSTZ.get(key);
        }
    }
}
