const Sequelize = require('sequelize')

const sequelize = new Sequelize('glowing-potato-db', 'postgres', '',
  { host: '127.0.0.1', dialect: 'postgres' })

const Post = sequelize.define('post', {
  tags: { type: Sequelize.ARRAY(Sequelize.TEXT), allowNull: false },
  title: { type: Sequelize.TEXT, allowNull: false },
  description: { type: Sequelize.TEXT, allowNull: false },
  body: { type: Sequelize.TEXT, allowNull: false }
})

const dummyMarkdown = `
# H1
## H2
### H3
#### H4
##### H5
###### H6

Alternatively, for H1 and H2, an underline-ish style:

Alt-H1
======

Alt-H2
------ 

Emphasis, aka italics, with *asterisks* or _underscores_.

Strong emphasis, aka bold, with **asterisks** or __underscores__.

Combined emphasis with **asterisks and _underscores_**.

Strikethrough uses two tildes. ~~Scratch this.~~
`

const dummyPosts = [
    {
        tags: [ 'first', 'javascript' ],
        title: 'A very 1st DummyPost',
        description: 'This post contains markdown. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        body: dummyMarkdown
    },
    {
        tags: [ 'second', 'go-lang' ],
        title: 'DummyPost the 2nd',
        description: 'This post also contains markdown. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        body: dummyMarkdown
    },
    {
        tags: [ 'third', 'go-lang' ],
        title: 'DummyPost the 3rd',
        description: 'This post also contains markdown. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        body: dummyMarkdown
    }
]

dummyPosts.map(p => {
    sequelize.sync()
        .then(() => Post.create(p))
        .catch(e => {
            console.log('Error:', e)
            process.exit(1)
        })
})

setTimeout(() => {
    console.log('\n\nSuccess!')
    process.exit(0)
}, 2000)
