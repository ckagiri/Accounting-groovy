package accounting

import de.gzockoll.types.money.Money

class Posting {
    Date date
    String memo
    boolean posted=false

    Set entries = []

    static hasMany = [entries:Entry]
    static constraints = {
    }

    private add(Money amount, Account account) {
        assertNotPosted();
        addToEntries (new Entry(account, amount, this))
    }

    private void assertCanPost() {
        if (!canPost())
            throw new IllegalStateException("balance must be zero");
    }

    boolean canPost() {
        return hasEntries() && isBalanced()
    }

    boolean hasEntries() {
        entries.size() > 0
    }

    private boolean isBalanced() {
        balance().value == 0G
    }

    def balance() {
        assert entries != null
        entries.collect{it.amount}.sum()
    }

    def assertNotPosted() {
        if (posted)
            throw new IllegalStateException("Posting has been posted already!");
    }

    def post() {
        assertNotPosted()
        assertCanPost()
        // AuditLog.add("transaction.post",this);
        entries.each { it.post() }
        posted = true
        this
    }

    def inverse(aDate) {
        def trans = new Posting(date:d,memo: "Storno: $memo")
        entries.each { trans.add(new Entry(it.account, it.amount.negate())) }
        trans;
    }

    def credit(Money amount, Account account) {
        add(amount,account)
        this
    }

    def debit(Money amount, Account account) {
        add(amount.negate(),account)
        this
    }
}
