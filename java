/**
 * VendingMachine class.
 * 
 * @author marcus tran
 * @version 3/6/23
 */
public class VendingMachine {
    public static final int DEFAULT_SIZE = 15;
    protected static int totalProfit;
    protected int machineProfit;
    private Slot[] slots;

    /**
     * Default constructor.
     */
    public VendingMachine() {
        this(DEFAULT_SIZE);
    }

    /**
     * Constructs with a specific size.
     * 
     * @param size number of slots to create
     */
    public VendingMachine(int size) {
        slots = new Slot[size];
        for (int i = 0; i < size; i++) {
            slots[i] = new Slot();
        }
    }

    /**
     * Constructs with specific size and product.
     * 
     * @param size number of slots to create
     * @param product product to fill slots with
     */
    public VendingMachine(int size, Product product) {
        slots = new Slot[size];
        for (int i = 0; i < size; i++) {
            slots[i] = new Slot(product);
            machineProfit -= product.getCost();
            totalProfit -= product.getCost();
        }
    }

    public void load() {
        Product generic = new Product();
        int totalCost = 0;

        for (Slot slot : slots) {
            if (slot == null) {
                continue;
            }

            int amountFilled = slot.load(generic);
            totalCost += amountFilled * generic.getCost();
        }

        machineProfit -= totalCost;
        totalProfit -= totalCost;
    }

    public void load(int slotNum, int count, Product product) throws IllegalArgumentException {
        if (slotNum < 0 || slotNum >= slots.length) {
            throw new IllegalArgumentException("Invalid slot number.");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Count can't be negative or 0.");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        int used = slots[slotNum].load(product, count);
        machineProfit -= product.getCost() * used;
        totalProfit -= product.getCost() * used;
    }

    public Product nextProduct(int slotNum) throws IllegalArgumentException {
        if (slotNum < 0 || slotNum >= slots.length) {
            throw new IllegalArgumentException("Invalid slot number");
        }
        Slot slot = slots[slotNum];
        Product product = slot.nextProduct();
        return product;
    }

    public boolean buy(int slotNum) {
        if (slotNum < 0 || slotNum >= slots.length) {
            throw new IllegalArgumentException("Invalid slot number.");
        }

        Product product = nextProduct(slotNum);

        if (product == null) {
            return false;
        }

        machineProfit += product.getPrice();
        totalProfit += product.getPrice();

        slots[slotNum].buyOne();

        return true;
    }

    public int getSlotCount() {
        return slots.length;
    }

    public static int getTotalProfit() {
        return totalProfit;
    }

    public static void resetTotalProfit() {
        totalProfit = 0;

    }

    public int getMachineProfit() {
        return machineProfit;
    }

    public String toString() {
        double tp = totalProfit / 100f;
        double mp = machineProfit / 100f;
        StringBuilder sb = new StringBuilder();
        sb.append("Vending Machine\n");
        sb.append("SlotCount: " + slots.length + " of\n");

        for (int i = 0; i < slots.length; i++) {
            sb.append(slots[i].nextProduct().toString() + "\n");
        }

        sb.append("Total Profit: " + String.format("%.2f", tp) + " Machine Profit: " + String.format("%.2f", mp) + ".");
        return sb.toString();
    }

}
