package com.jerry;

import java.util.*;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击间距中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        //TIP 当文本光标位于高亮显示的文本处时按 <shortcut actionId="ShowIntentionActions"/>
        // 查看 IntelliJ IDEA 建议如何修复。
        System.out.println("刘谦魔术复刻/揭秘");
        System.out.println("请输入程序类型:1为复刻,输入2为揭秘,按回车键结束");
        Scanner scanner = new Scanner(System.in);
        int type = getTypeOrSex(scanner);

        //备牌
        System.out.println("请输入4位任意字符,以“,”分割,代表4张牌");
        LinkedList<String> cardList = getCards(scanner);

        //撕牌
        for (int i = 0; i < 4; i++) {
            cardList.add(cardList.get(i));
        }

        //根据名字翻转
        System.out.printf("系统已经帮您自动撕开卡牌,撕开后的卡牌顺序为:%s%n",cardList);
        System.out.println("还请您接着输入您名字的长度");
        getTurnOverByName(scanner,cardList);
        if (type==2){
            System.out.printf("根据名字翻转后的卡片顺序为:%s%n",cardList);
        }

        //把头三张卡牌插到中间的牌堆里面
        insertMiddle(3,cardList);
        if (type==2){
            System.out.printf("头三张插入牌堆中间后牌的顺序为:%s%n",cardList);
        }

        //获取到要对比的卡牌
        String secretCard = cardList.poll();
        if (type==2){
            System.out.printf("取出来的秘密卡牌值为:%s%n",secretCard);
        }

        //根据地域再次项中间插牌
        System.out.println("请输入您的地理位置:1.南方,2.北方,3.不知道");
        insertMiddleByPosition(scanner,cardList);
        if (type==2){
            System.out.printf("根据地域挪动后卡牌堆的排序值为:%s%n",cardList.toString());
        }

        //根据性别弃牌
        System.out.println("请输入您的性别:1.男,2.女");
        dropCardBySex(scanner,cardList);
        if (type==2){
            System.out.printf("根据性别弃牌后卡牌堆的排序值为:%s%n",cardList.toString());
        }

        //把对首的卡牌挪到队尾,需要挪7次,分别对应:见证奇迹的时刻
        cardList = moveFirstToLast(cardList);

        //把队首的卡牌挪到队尾,第二张卡牌弃掉,直到剩下一张卡牌
        String card = moveAndDrop(cardList,type);
        System.out.printf("最后剩下的卡牌为:%s,最开始留下的卡牌为%s.",card,secretCard);
        System.out.println("恭喜你,表演结束");
    }

    /**
     * 把队首的卡牌挪到队尾,第二张卡牌弃掉,直到剩下一张卡牌
     * @param cardList 卡牌集合
     * @param type 类型
     * @return 最后留下的那张卡牌
     */
    private static String moveAndDrop(LinkedList<String> cardList, int type) {
        while (cardList.size()!=1){
            cardList.offer(cardList.poll());
            cardList.poll();
            if (type==2){
                System.out.println("开始丢牌,丢弃后的牌堆为:"+cardList.toString());
            }
        }
        return cardList.poll();
    }

    /**
     * 把首张卡牌挪到最底下,需要挪7次,分别对应:见证奇迹的时刻
     * @param cardList 卡牌集合
     * @return 操作后的卡牌
     */
    private static LinkedList<String> moveFirstToLast(LinkedList<String> cardList) {
        for (int i = 0; i < 7; i++) {
            cardList.offer(cardList.poll());
        }
        return cardList;
    }

    /**
     * 根据性别弃牌: 男生弃1张,女生弃2张
     * @param scanner 共享输入监听对象
     * @param cardList 卡牌集合
     * @return 操作后的卡牌
     */
    private static LinkedList<String> dropCardBySex(Scanner scanner, LinkedList<String> cardList) {
        int sex = getTypeOrSex(scanner);
        for (; sex >-1 ; sex--) {
            cardList.poll();
        }
        return cardList;
    }

    /**
     * 根据地域再次项中间插牌
     * @param scanner 共享输入监听对象
     * @param cardList 卡牌集合
     * @return 操作后的卡牌
     */
    private static LinkedList<String> insertMiddleByPosition(Scanner scanner, LinkedList<String> cardList) {
        String position = scanner.nextLine();
        while (!(position.equals("1")||position.equals("2")||position.equals("3"))){
            System.out.println("您的输入有误,请重新输入:您的地理位置:1.南方,2.北方,3.不知道");
            position = scanner.nextLine();
        }
        insertMiddle(Integer.parseInt(position),cardList);
        return cardList;
    }

    /**
     * 头三张插入中间
     * @param cardNum 需要插入中间的卡牌数量
     * @param cardList 卡牌集合
     * @return 操作后的卡牌
     */
    private static LinkedList<String> insertMiddle(int cardNum, LinkedList<String> cardList) {
        for (int i = 0; i < cardNum; i++) {
            //这里因为每次只挪了首位,所以在计算出中间的位置((cardList.size() - cardNum)/2)后,还要加上没有挪动过的牌数(cardNum-1)
            cardList.add((cardList.size() - cardNum)/2+cardNum-1,cardList.poll());
        }
        return cardList;
    }

    /**
     * 根据名字的字数把牌堆的头挪到牌堆尾
     * @param scanner 共享输入监听对象
     * @param cardList 卡牌集合
     * @return 翻转后的卡牌
     */
    private static LinkedList<String> getTurnOverByName(Scanner scanner, LinkedList<String> cardList) {
        String turnOverCount = getNotBlankCard(scanner);
        while (!turnOverCount.matches("^[0-9]+$")) {
            System.out.println("规则验证错误,还请输入您的名字长度:");
            turnOverCount = scanner.nextLine();
        }
        //开始翻转
        for (int i = 0; i < Integer.parseInt(turnOverCount); i++) {
            cardList.offer(cardList.poll());
        }
        return cardList;
    }



    /**
     * 获取所有的8张模拟卡牌(以“,”分割的方式)
     * @param scanner 共享输入监听对象
     * @return 8张模拟卡牌
     */
    private static LinkedList<String> getCards(Scanner scanner) {
        String inCard = getNotBlankCard(scanner);
        LinkedList<String> cardList = new LinkedList<>(Arrays.stream(inCard.split(",")).filter(card -> !card.isBlank()).toList());
        do{
            if (cardList.size()<4){
                System.out.printf("您输入了%d个字符%s,还需要输入%d个字符,请再次输入(还是以“,”分隔呦\uD83D\uDE0A):%n",cardList.size(), cardList,4-cardList.size());
                inCard = getNotBlankCard(scanner);
                List<String> list = Arrays.stream(inCard.split(",")).filter(card -> !card.isBlank()).toList();
                cardList.addAll(list);
            }else{
                if (cardList.size()>4)
                    System.out.println("您输入的个数超过了4个,现只取前4个");
               cardList.subList(0,3);
            }
            //长度校验
        }while (cardList.size()!=4);
        return cardList;
    }

    /**
     * 过滤掉空输入
     * @param scanner 共享输入监听对象
     * @return 输入的卡牌
     */
    private static String getNotBlankCard(Scanner scanner) {
        String inCard = scanner.nextLine();
        while (inCard.isBlank()){
            System.out.println("请不要直接敲回车哦,还请再次输入");
            inCard = scanner.nextLine();
        }
        return inCard;
    }

    private static int getTypeOrSex(Scanner scanner) {
        int type = 0;
        do {
            String typeScan = scanner.nextLine();
            if (typeScan.equals("1")||typeScan.equals("2")){
                type = Integer.parseInt(typeScan);
            }else {
                System.out.println("您输入正确的类型,请重试.输入1为复刻,输入2为揭秘");
            }
        }while (type==0);
        return type;
    }

}