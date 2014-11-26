package com.nordnet.opale.finder.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe va contenir la liste des elements d une page.
 * 
 * @param <E>
 *            type element
 * 
 * @author anisselmane.
 */
public class Page<E> {

	/**
	 * numero de page.
	 */
	private int pageNumber;

	/**
	 * pages disponible.
	 */
	private int pagesAvailable;

	/** The page items. */
	private List<E> pageItems = new ArrayList<E>();

	/**
	 * 
	 * @param pageNumber
	 *            {@link Page#pageNumber}
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * 
	 * @param pagesAvailable
	 *            {@link #pagesAvailable}.
	 */
	public void setPagesAvailable(int pagesAvailable) {
		this.pagesAvailable = pagesAvailable;
	}

	/**
	 * 
	 * @param pageItems
	 *            {@link #pageItems}
	 */
	public void setPageItems(List<E> pageItems) {
		this.pageItems = pageItems;
	}

	/**
	 * 
	 * @return {@link #pageNumber}
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * 
	 * @return {@link #pagesAvailable}.
	 */
	public int getPagesAvailable() {
		return pagesAvailable;
	}

	/**
	 * 
	 * @return {@link #pageItems}
	 */
	public List<E> getPageItems() {
		return pageItems;
	}
}