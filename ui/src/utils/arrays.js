const updateItemInArray = (array, item) => {

    const index = array.findIndex(it => it.id === item.id)

    if (index === -1) {

        array.push(item)

    } else {

        array[index] = item

    }

}

const removeItemFromArray = (array, item) => {

    const index = array.findIndex(it => it.id === item.id)

    array.splice(index, 1)

}

const sortArrayByName = arrays => arrays.sort((a, b) => {

    const nameA = a.name.toLowerCase();

    const nameB = b.name.toLowerCase();

    if (nameA > nameB) {

        return 1

    }

    if (nameA < nameB) {

        return -1

    }

    return 0

})

const filterArrayByName = (arrays, { name }) =>
    sortArrayByName(arrays.filter(item => item.name.toLowerCase().includes(name.toLowerCase())))

export { updateItemInArray,
         removeItemFromArray,
         sortArrayByName,
         filterArrayByName }