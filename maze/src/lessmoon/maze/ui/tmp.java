/*
        jpc.add(jp);
        java.util.Timer timer = new java.util.Timer(true);
        final JPanel p = new JPanel(){
            public void paint(Graphics g){
                super.paint(g);
                g.setColor(Color.YELLOW);
                g.drawLine(0,0,0,MAP_HEIGHT * TILE_WIDTH);
                g.setColor(Color.RED);
                g.drawString("" + count--,50,50);
                System.out.println("ok");
            }
            int count = 200;
        };
        timer.schedule(new java.util.TimerTask(){
            public void run(){
                System.out.println("www");
                p.repaint();
                System.out.println("ddd");
            }},50,500);
        p.setPreferredSize(new Dimension(80,MAP_HEIGHT * TILE_WIDTH + 1));
        p.add(new JButton("OK"));

        jpc.add(p);
*/