class Calculator {

    static int calculateWaterAmount(int[] landscape) {
        int waterAmount = 0;

        if (landscape == null || landscape.length == 0) {
            return waterAmount;
        }

        int[] leftHigh = new int[landscape.length + 1];
        leftHigh[0] = 0;
        for (int i = 0; i < landscape.length; i++) {
            leftHigh[i + 1] = Math.max(leftHigh[i], landscape[i]);
        }

        int rightHigh = 0;
        for (int i = landscape.length - 1; i >= 0; i--) {
            rightHigh = Math.max(landscape[i], rightHigh);
            waterAmount += Math.min(leftHigh[i], rightHigh) > landscape[i] ?
                    Math.min(leftHigh[i], rightHigh) - landscape[i] : 0;
        }

        return waterAmount;
    }
}
