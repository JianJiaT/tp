package brokeculator.enumerators;

import brokeculator.dashboard.Dashboard;
import brokeculator.expense.Expense;
import brokeculator.storage.parsing.FileKeyword;
import brokeculator.storage.parsing.SaveableType;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Category {
    private static final int LIST_ALL_EXPENSES = -1;
    private static final String DASH = "- ";
    private static Dashboard dashboard = null;
    private static boolean isDashboardSet = false;
    private static Set<String> categories = new HashSet<>();
    /**
     * Sets the dashboard for the Category class
     * @param dashboard Dashboard to be set
     */
    public static void setDashboard(Dashboard dashboard) {
        if (isDashboardSet) {
            return;
        }
        Category.isDashboardSet = true;
        Category.dashboard = dashboard;
    }
    public static String addCategory(String category) {
        category = category.toUpperCase();
        if (categories.contains(category)) {
            return "Category already exists";
        }
        categories.add(category);
        return "Category added: " + category;
    }
    public static ArrayList<String> getCategoryList() {
        return new ArrayList<>(categories);
    }
    public static boolean isValidCategory(String category) {
        return categories.contains(category);
    }
    public static String removeCategory(String category) {
        category = category.toUpperCase();
        if (isCategoryUsed(category)) {
            return "Cannot remove category that is in use";
        }
        if (!categories.contains(category)) {
            return "Category does not exist";
        }
        categories.remove(category);
        return "Category removed: " + category;
    }
    private static boolean isCategoryUsed(String category) {
        assert dashboard != null : "Dashboard should not be null";
        ArrayList<Expense> expenseList = dashboard.getExpenseManager().listExpenses(LIST_ALL_EXPENSES);
        for (Expense expense : expenseList) {
            if (expense.getCategory() == null) {
                continue;
            }
            if (expense.getCategory().equals(category)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Returns a string representation of all categories, appended with a dash for each category
     * @return String representation of all categories
     */
    public static String getCategoryListString() {
        StringBuilder sb = new StringBuilder();
        for (String category : categories) {
            sb.append(DASH).append(category).append(System.lineSeparator());
        }
        return sb.toString();
    }
    /**
     * Returns a string representation of all categories, formatted for saving
     * @return String representation of all categories, formatted for saving
     */
    public static String getCategoriesStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        for (String category : categories) {
            String finalExpenseString = FileKeyword.formatWithKeyword(SaveableType.CATEGORY, category);
            sb.append(finalExpenseString);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
